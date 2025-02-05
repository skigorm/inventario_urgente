package br.com.devolucao.backend.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.devolucao.backend.util.message.MessageBundle;
import br.com.devolucao.backend.util.message.MessageService;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(ApplicationServiceException.class)
    public ResponseEntity<MessageService> handleApplicationServiceException(ApplicationServiceException ex) {
        LOG.log(Level.FINE, "Problema na execução", ex);
        MessageService messageService = new MessageService(ex.getMessage(), ex.getErrorList());
        return ResponseEntity.status(ex.getStatusCode()).body(messageService);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageService> handleGenericException(Exception ex) {
        LOG.log(Level.SEVERE, "Erro na execução", ex);
        MessageService messageService = new MessageService(MessageBundle.getMessage("generico.erro"),
                null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageService);
    }
}