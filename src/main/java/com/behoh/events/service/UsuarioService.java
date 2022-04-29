package com.behoh.events.service;

import com.behoh.events.exception.EntidadeNaoEncontradaException;
import com.behoh.events.exception.RegraDeNegocioException;
import com.behoh.events.model.EventoEntity;
import com.behoh.events.model.UsuarioEntity;
import com.behoh.events.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Responsável por resolver lógicas de negócio de acesso
 *  ao banco de dados no que se refere a entidade Usuario.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Persiste um novo usuario.
     * @param usuario objeto contendo as inforamcoes basicas para cadastro
     * @return usuario contendo o ID gerado
     */
    public UsuarioEntity save (UsuarioEntity usuario){
        return usuarioRepository.save(usuario);
    }

    /**
     * Busca um usuario pelo ID.
     * @param id Id do usuario a ser buscado.
     * @return objeto Optional contendo Usuario encontrado ou podendo estar vazio caso contrário.
     * @throws EntidadeNaoEncontradaException Usuário não encontrado.
     */
    public UsuarioEntity find(Integer id) throws EntidadeNaoEncontradaException {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);

        if(usuario.isEmpty()){
            throw new EntidadeNaoEncontradaException("Usuário não encontrado");
        }

        return usuario.get();
    }

    /**
     * Inscreve usuário em evento.
     * @param usuario Usuário a ser inscrito.
     * @param evento Evento a se inscrever.
     * @return Usuário inscrito em evento.
     * @throws RegraDeNegocioException Quebra de regras de negocio.
     */
    public UsuarioEntity inscreverEm(
            UsuarioEntity usuario, EventoEntity evento)
            throws RegraDeNegocioException {

        // --- Há vagas disponiveis? ---
        Integer vagasOcupadas = evento.getUsuariosInscritos().size();
        Integer vagasDisponiveis = evento.getVagas() - vagasOcupadas;
        if(vagasDisponiveis < 1) {
            throw new RegraDeNegocioException("O evento não possui mais vagas disponíveis.");
        }

        // --- Usuário ja inscrito? ---
        if(estaInscritoEm(usuario, evento)){
            throw new RegraDeNegocioException("Usuário já inscrito no evento.");
        }

        // --- Evento já iniciado? ---
        LocalDateTime agora = LocalDateTime.now();
        if(evento.getInicio().isBefore(agora)){
            throw new RegraDeNegocioException("Evento já iniciado.");
        }

        // --- Persistindo inscricao ---
        usuario.getEventosInscritos().add(evento);
        return save(usuario);
    }

    /**
     * Cancela inscriçao de usuário em evento.
     * @param usuario Usuário a remover inscriçao.
     * @param evento Evento a ser removido.
     * @return Usuário atualizado
     * @throws RegraDeNegocioException Quebra de regras de negocio.
     */
    public UsuarioEntity cancelarInscricao
            (UsuarioEntity usuario, EventoEntity evento)
            throws RegraDeNegocioException{

        // --- Usuario ja entrou no evento? ---
        EventoEntity participandoEm = usuario.getParticipaEvento();
        if(participandoEm != null && participandoEm.getId().equals(evento.getId()) ){
            throw new RegraDeNegocioException("Usuário já está participando do evento.");
        }

        // --- Usuário está inscrito no evento? ---
        if(!estaInscritoEm(usuario,evento)){
            throw new RegraDeNegocioException("Usuário não está inscrito no evento.");
        }

        // --- Persistindo cancelamento ----
        usuario.getEventosInscritos().removeIf(
                // removendo evento da lista de inscricao
                e -> e.getId().equals(evento.getId())
        );

        return save(usuario);
    }

    /**
     * Persiste entrada de usuário em evento.
     * @param usuario Usuário a entrar.
     * @param evento Evento a ser entrado.
     * @return Usuário atualizado.
     * @throws RegraDeNegocioException Quebra de regras de negócio.
     */
    public UsuarioEntity entrarEmEvento
            (UsuarioEntity usuario, EventoEntity evento)
            throws RegraDeNegocioException{

        // --- Usu já está no evento? ---
        if( usuario.getParticipaEvento() != null &&
                usuario.getParticipaEvento().getId().equals(evento.getId())){
            throw new RegraDeNegocioException("Usuário já está participando do evento.");
        }

        // --- Usuário inscrito no evento? ---
        if(!estaInscritoEm(usuario, evento)){
            throw new RegraDeNegocioException("Usuário não está inscrito no evento.");
        }

        // --- Cedo demais para entrar? ---
        LocalDateTime agora = LocalDateTime.now();
        Long  minutosDeDiferenca = ChronoUnit.MINUTES.between(agora, evento.getInicio());
        if(minutosDeDiferenca > 60) {
            throw new RegraDeNegocioException(
                    "Só é possível entrar no evento 1 hora antes do início." +
                    "(início em: "+evento.getInicio()+")");
        }

        // --- Evento já terminou? ---
        if(evento.getFim().isBefore(agora)){
            throw new RegraDeNegocioException("Evento já terminou.");
        }

        // --- Persistindo entrada ---
        usuario.setParticipaEvento(evento);

        return save(usuario);
    }

    /**
     * Confere se usuario está inscrito em evento com ID especificado.
     * @param eventoID ID do evento a ser conferido.
     */
    //TODO: mudar lógica para utilizar query de UsuarioRepository
    public boolean estaInscritoEm(UsuarioEntity usuario, EventoEntity evento){

        // filtrando lista por ID e conferindo se exite resultado
        return usuario.getEventosInscritos()
                .stream().filter(e -> e.getId().equals(evento.getId()))
                .findFirst().isPresent();
    }

}
