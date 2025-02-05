package br.com.devolucao.backend.util.message;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que carrega o arquivo de mensagens da aplicação. 
 */
public class MessageBundle {

	private static ResourceBundle messageResources;
	private static String defaultMessage;
	private static final Logger LOG = Logger.getLogger(MessageBundle.class.getName());

	/**
	 * Carrega o ResourceBundle da aplicação. Também carrega a mensagem padrão com o
	 * código "message.default" que pode ser definida no resourceBundle. Caso ela
	 * não esteja definida no messageBundle, uma mensagem padronizada é utilizada:
	 * 'Ocorreu um erro inesperado. Contacte o administrador do sistema.' Esta
	 * mensagem padrão é utilizada quando o código de alguma mensagem não é
	 * encontrado no resourceBundle.
	 */
	static {
		try {
			messageResources = ResourceBundle.getBundle("ApplicationMessages");
		} catch (Exception e) {
			LOG.log(Level.SEVERE,"Não foi possível acessar arquivo de properties com as mensagens do sistema.", e);
			messageResources = null;
		}

		// carrega msg padrao
		defaultMessage = getMessage("message.default");
		if (defaultMessage == null || "".equals(defaultMessage)) {
			defaultMessage = "Ocorreu um erro inesperado. Contacte o administrador do sistema.";
		}
	}

	/**
	 * Recupera a mensagem através da sua chave. Se não a encontrar, utiliza a
	 * mensagem padrão e a retorna.
	 * 
	 * @param key
	 *            chave da mensagem. Ex: "mensagem.login"
	 * @return String mensagem definida no arquivo properties
	 */
	public static String getMessage(String key) {
		String msg = null;

		try {
			msg = messageResources.getString(key);
		} catch (MissingResourceException mre) {
			LOG.log(Level.FINE,"Código da mensagem não encontrado", mre);
			msg = defaultMessage;
		} catch (Exception e) {
			LOG.log(Level.SEVERE,"Não foi possível acessar arquivo de properties.", e);
			messageResources = null;
			msg = defaultMessage;
		}

		// Retornando mensagem
		return (msg == null ? "" : msg);
	}

	/**
	 * Recupera a mensagem através da sua chave. Se não a encontrar, procura por uma
	 * mensagem chamada "message.default" e a retorna. Substitui as chaves com os
	 * parametros passados. não realiza nenhum teste para verificar se o número de
	 * parâmetros coincide com o recebido. Para ocorrer a substituição, a string
	 * deve conter o seguinte padrão no arquivo de properties:
	 * mensagem.login=bem-vindo ao sistema {0}, {1} No lugar de {0} será colocado o
	 * item que está na posição 0 do array, no lugar de {1} o que está na 1, e assim
	 * por diante
	 * 
	 * @param key
	 *            chave da mensagem. Ex: "mensagem.login"
	 * @param args
	 *            prâmetros que serão substituídos na mensagem
	 * @return String mensagem
	 */
	public static String getMessage(String key, String[] args) {
		String msg = getMessage(key);

		if ("".equals(msg)) {
			return "";
		}

		MessageFormat mf = new MessageFormat(msg);
		msg = mf.format(args);
		return (msg != null ? msg : "");
	}
}
