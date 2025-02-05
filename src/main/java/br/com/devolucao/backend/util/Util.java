package br.com.devolucao.backend.util;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

import br.com.devolucao.backend.exception.ApplicationServiceException;

public class Util implements Serializable {
	
	private static final Logger LOG = Logger.getLogger(Util.class.getName());

	private static final long serialVersionUID = -5381519579703509328L;
	private static final String[] meses = { "Janeiro", "Fevereiro", "Mar\u00E7o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" };

	
	
	
	
	public static String getNomeMetodo() {
		String methodName = "";
		try {
			methodName = Thread.currentThread().getStackTrace()[2].getMethodName(); //Pega o método que chamou
			
			//methodName = Thread.currentThread().getStackTrace()[1].getMethodName(); //Pega o método atual
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			methodName = "{ ERRO ao recuperar o nome do metodo }";
		}
		return methodName;
	}
	
	
	/**
	 * Verificar se número passado como parametro é null ou zero
	 * 
	 * @return {@link Boolean}
	 */
	public static boolean isNullOrZero(Number numero) {
		return numero == null || BigDecimal.valueOf(numero.doubleValue()).compareTo(BigDecimal.ZERO) == 0;
	}

	/**
	 * Verificar se número passado como parametro não é null
	 * 
	 * @return boolean
	 */
	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	/**
	 * Verificar se número passado como parametro é null
	 * 
	 * @return boolean
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

 
	/**
	 * Verifica se uma string é vazia ou nula
	 * 
	 * @param string
	 * @return boolean
	 */
	public static boolean isBlank(String string) {
		if (string != null) {
			return string.trim().isEmpty();
		}
		return true;
	}

	/**
	 * Valida se e nulo ou se a string, depois de trim, e vazia
	 */
	public static boolean isNullOrEmpty(String s) {

		if (s == null) {
			return true;
		}

		for (int i = 0; i < s.length(); i++) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Testa se o valor informado e numerico ou nao.
	 */
	public static Boolean isNumber(String valor) {

		Boolean retorno;

		try {
			@SuppressWarnings("unused")
			Long teste = Long.valueOf(valor);
			retorno = Boolean.TRUE;

		} catch (Exception e) {
			// Se ocorrer erro no parse, nao e numero
			retorno = Boolean.FALSE;
		}
		return retorno;
	}

	/**
	 * Testa se o valor informado e data ou nao.
	 * 
	 * @param valor
	 *            (DD/MM/AA | DD/MM/AAAA)
	 */
	public static Boolean isDate(String valor) {

		Integer dia = Integer.valueOf(valor.substring(0, 2));
		Integer mes = Integer.valueOf(valor.substring(3, 5));
		Integer ano = Integer.valueOf(valor.substring(6, 10));

		if (mes < 1 || mes > 12) {
			return Boolean.FALSE;
		}

		if (dia < 1 || dia > 31) {
			return Boolean.FALSE;
		}

		if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia == 31) {
			return Boolean.FALSE;
		}

		if (mes == 2) {
			boolean isleap = (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0));
			if (dia > 29 || (dia == 29 && !isleap)) {
				return Boolean.FALSE;
			}
		}

		return Boolean.TRUE;
	}

	/**
	 * Verifica se a data informado e valida ou nao.
	 * 
	 */
	public static boolean isDate(Date data) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);

		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH) + 1;
		// int ano = calendar.get(Calendar.YEAR);

		try {
			if (mes < 1 || mes > 12) {
				return false;
			}

			if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 12) {
				if (dia < 1 || dia > 31) {
					return false;
				}

			} else if (mes == 4 || mes == 6 || mes == 9 || mes == 10 || mes == 11) {
				if (dia < 1 || dia > 30) {
					return false;
				}

			} else if (mes == 2) {
				if (dia < 1 || dia > 29) {
					return false;
				}
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Formatar CNPJ
	 *
	 * @param cnpj
	 * @return String
	 */
	public static String formatarCNPJ(String cnpj) {
		cnpj = Util.soNumeros(cnpj);
		return formatString(cnpj, "##.###.###/####-##");
	}

	/**
	 * Formatar EMPENHO
	 *
	 * @param valor
	 * @return String
	 */
	public static String formatarNumeroEmpenho(String valor) {
		valor = Util.soNumeros(valor);
		return formatString(valor, "##.##.####/#/#####-#");
	}

	/**
	 * Formatar CPF
	 *
	 * @param cpf
	 * @return String
	 */
	public static String formatarCPF(String cpf) {
		cpf = Util.soNumeros(cpf);
		return formatString(cpf, "###.###.###-##");
	}

	/**
	 * Remove todos os caracteres exceto números
	 * 
	 * @param string
	 * @return
	 */
	public static String soNumeros(String string) {
		if (!isBlank(string)) {
			string = string.replaceAll("[^0123456789]", "");
		}
		return string;
	}

	private static String formatString(String str, String mask) {
		if (str == null || str.trim().isEmpty()) {
			return "";
		}
		try {
			MaskFormatter mf = new MaskFormatter(mask);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(str);
		} catch (ParseException e) {
			throw new RuntimeException("Erro ao formatar string. " + e.getMessage());
		}
	}

	/**
	 * Converte um Date para uma String no formato dd/mm/yyyy hh:mm:ss
	 * 
	 * @param data
	 *            - Data a ser convertida
	 * @return string - dd/mm/yyyy hh:mm:ss
	 **/
	public static String formataDataHora(java.util.Date data) {

		String formatedDate = "";

		if (data != null) {
			formatedDate = Util.formataData(data) + " " + Util.formataHora(data);
		}

		return formatedDate;
	}

	/**
	 * Converte um Date para uma String no formato HH:mm:ss
	 * 
	 * @param data
	 *            - Data a ser convertida
	 * @return string - HH:mm:ss
	 */
	public static String formataHora(java.util.Date data) {

		String formato = "HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		return (data != null ? formatter.format(data) : "");
	}

	/**
	 * Converte um Date para uma String no formato HH:mm
	 * 
	 * @param data
	 *            - Data a ser convertida
	 * @return string - HH:mm
	 */
	public static String formataHoraCurta(java.util.Date data) {

		String formato = "HH:mm";
		String strRetorno;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);

		} else {
			strRetorno = "";
		}
		return strRetorno;
	}

	/**
	 * Converte um Date para uma String no formato dd/mm/yyyy<br>
	 * 
	 * @param data
	 *            - Data a ser convertida
	 * @return string - dd/mm/yyyy
	 */
	public static String formataData(java.util.Date data) {

		String formato = "dd/MM/yyyy";
		String strRetorno = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);
		}

		return strRetorno;
	}
	
	/**
	 * Converte um Date para uma String no formato dd/mm/yyyy<br>
	 * 
	 * @param data
	 *            - Data a ser convertida
	 * @return string - dd/mm/yyyy
	 */
	public static String formataAno(java.util.Date data) {
		
		String formato = "yyyy";
		String strRetorno = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);
		
		if (data != null) {
			strRetorno = formatter.format(data);
		}
		
		return strRetorno;
	}

	/**
	 * Converte um Date para uma String no formato yyyy-mm-dd<br>
	 * 
	 * @param data
	 *            - Data a ser convertida
	 * @return string - yyyy-mm-dd
	 */
	public static String formataDataInvertida(java.util.Date data) {

		String formato = "yyyy-MM-dd";
		String strRetorno = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);
		}

		return strRetorno;
	}

	/**
	 * Converte um Date para uma String no formato yymmdd<br>
	 * 
	 * @param data
	 *            - Data a ser convertida
	 * @return string - yymmdd
	 */
	public static String formataDataShort(java.util.Date data) {

		String formato = "yyMMdd";
		String strRetorno = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);
		}

		return strRetorno;
	}

	/**
	 * Formata a data em (dd/MM/yyyy)
	 */
	public static Date formataData(String data) {

		if (data == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date ret = null;

		try {
			ret = sdf.parse(data);

		} catch (ParseException e) {
			return ret;
		}

		return ret;
	}

	/**
	 * Transforma String de data no formato norte-americano para Date
	 * 
	 * @param data
	 *            - no formato MM/dd/yyyy
	 */
	public static Date formataDataInglesa(String data) {

		if (data == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date ret;

		try {
			ret = sdf.parse(data);

		} catch (ParseException e) {
			ret = null;
		}

		return ret;
	}

	/**
	 * Converte um periodo de tempo em milisegundos para o formato HH:mm:ss
	 */
	public static String formatarPeriodoTempo(long milisegundos) {

		double sec = milisegundos / 1000.0; // equivalente em millisegundos
		double min = sec / 60.0; // equivalente em segundos
		double hora = min / 60.0; // equivalente em horas
		double resto = 0.0;

		double intHora = (int) hora; // horas inteiras

		resto = hora - intHora; // resto, em horas

		double intMin = (int) (resto * 60); // minutos inteiros

		resto = resto - (intMin / 60); // resto, em horas

		double intSec = (int) (resto * 60 * 60); // segundos inteiros

		NumberFormat formatter = NumberFormat.getIntegerInstance();

		formatter.setMinimumIntegerDigits(2);
		formatter.setMaximumFractionDigits(0);

		String tempo = formatter.format(intHora) + ":" + formatter.format(intMin) + ":" + formatter.format(intSec);

		return tempo;
	}

	/**
	 * Formata o nome do mes de acordo com o seu numero
	 * 
	 * @param numMes
	 *            - Mes a ser convertido
	 * @return string - Nome do mes
	 **/
	public static String nomeDoMes(int numMes) {

		if (numMes < 0 || numMes > 11) {
			return "";
		}

		String ret = "";
		switch (numMes) {
		case 0:
			ret = "Janeiro";
			break;
		case 1:
			ret = "Fevereiro";
			break;
		case 2:
			ret = "Mar\u00E7o";
			break;
		case 3:
			ret = "Abril";
			break;
		case 4:
			ret = "Maio";
			break;
		case 5:
			ret = "Junho";
			break;
		case 6:
			ret = "Julho";
			break;
		case 7:
			ret = "Agosto";
			break;
		case 8:
			ret = "Setembro";
			break;
		case 9:
			ret = "Outubro";
			break;
		case 10:
			ret = "Novembro";
			break;
		case 11:
			ret = "Dezembro";
			break;
		default:
			ret = null;
		}
		return ret;
	}

	/**
	 * Formata o numero para String, preenchendo com '0' ate a tamanho.
	 * 
	 * @return StringBuilder do tamanho do numero.
	 */
	public static StringBuilder numeroToString(Long numero, int tamanho) {

		String str = numero.toString();
		StringBuilder sb = new StringBuilder(tamanho + 1);
		int len = str.length();

		for (int i = 0; i < tamanho - len; i++) {
			sb.append('0');
		}

		sb.append(str);

		return sb;
	}

	/**
	 * Retorna a data atual por extenso.
	 *
	 * @param date
	 *            a ser formatada formato dd de mmmmmmm de yyyy.
	 * @return Retorna uma string representado a data passada como parametro.
	 */
	public static String dataPorExtenso(Date date) {

		GregorianCalendar data = new GregorianCalendar();
		data.setTime(date);
		int mes = data.get(GregorianCalendar.MONTH);

		return data.get(GregorianCalendar.DATE) + " de " + meses[mes] + " de " + data.get(GregorianCalendar.YEAR);
	}

	/**
	 * Recebe o mes como inteiro e retorna escrito por extenso
	 */
	public static String formataMes(int mes) {
		if (mes > 12 || mes < 1) {
			return "M\u00EAs inexistente";

		} else {
			return meses[mes - 1];
		}
	}

	/**
	 * Recebe uma string str e uma mascara mask onde , da esquerda para a direita, o
	 * caracter da mascara # e substituido por caracteres de str, enquanto os demais
	 * caracteres sao adicionados e string de retorno.
	 */
	public static String mascaraGenerica(String str, String mask) {

		if (str == null) {
			return null;
		}

		if (mask == null) {
			return str;
		}

		int i = str.length();
		int j = mask.length();
		Stack<String> pilha = new Stack<String>();

		for (/* nop; */; i > 0 && j > 0; i--, j--) {

			if (mask.substring(j - 1, j).equals("#")) {
				pilha.push(str.substring(i - 1, i));

			} else {
				pilha.push(mask.substring(j - 1, j));
				i++;
			}
		}
		if (i > j) {
			pilha.push(str.substring(0, i));
		}

		StringBuilder retorno = new StringBuilder();

		while (!pilha.isEmpty()) {
			retorno.append(pilha.pop());
		}

		return retorno.toString();
	}

	/**
	 * Completar com caracteres a esquerda ou a direita. <br>
	 * 
	 * @param arg
	 *            : valor a ser completado
	 * @param caracter
	 *            : caracter usado no preenchimento
	 * @param tamanho
	 *            : tamanho do retorno ja preenchido
	 * @param esquerda
	 *            : true - preencher a esquerda; false - preencher a direita
	 */
	public static String completarComCaracter(String arg, String caracter, int tamanho, boolean esquerda) {

		String retorno = arg;
		int size = tamanho - arg.length();

		for (int i = 1; i <= size; i++) {
			if (esquerda) {
				retorno = caracter.concat(retorno);

			} else {
				retorno = retorno.concat(caracter);
			}
		}

		return retorno;
	}

	/**
	 * Retorna um numero com formatado com 2 casas decimais. Formato de retorno
	 * 0000000,00
	 * 
	 * @param number
	 *            : number valor monetario
	 * @return String valor monetario formatado
	 */
	public static String formatarNumeroMonetario(double number) {

		// NumberFormat formatter = new DecimalFormat("###,###,###,###,###,###,##0.00");

		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		formatter.setMinimumFractionDigits(2);

		return formatter.format(number).replace("R$ ", "");
	}

	/**
	 * Retorna um numero com formatado com 2 casas decimais. Formato de retorno
	 * 0000000,00
	 * 
	 * @param number
	 *            : number valor monetario
	 * @return String valor monetario formatado
	 */
	public static String formatarNumeroMonetario(BigDecimal number) {

		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		formatter.setMinimumFractionDigits(2);

		return formatter.format(number).replace("R$ ", "");
	}

	/**
	 * Retorna um numero com formatado com a quantidade de casas decimais informada.
	 * Formato de retorno 0,0...
	 * 
	 * @param number
	 *            : number valor monetario
	 * @param casasDecimais
	 *            : quantidade de casas decimais desejada
	 * 
	 * @return String valor formatado
	 */
	public static String formatarNumeroDecimal(BigDecimal number, int casasDecimais) {

		NumberFormat formatter = new DecimalFormat("#0.0");

		formatter.setMinimumFractionDigits(casasDecimais);

		return formatter.format(number);
	}

	/**
	 * Retorna um numero com formatado com a quantidade de casas decimais informada.
	 * Formato de retorno 0,0...
	 * 
	 * @param number
	 *            : number valor monetario
	 * @param casasDecimais
	 *            : quantidade de casas decimais desejada
	 * 
	 * @return String valor formatado
	 */
	public static String formatarNumeroDecimal(double number, int casasDecimais) {

		NumberFormat formatter = new DecimalFormat("#0.0");

		formatter.setMinimumFractionDigits(casasDecimais);

		return formatter.format(number);
	}

	/**
	 * Gera um objeto Date a partir do dia (java.util.Date) e hora
	 * (java.lang.String).
	 */
	public static Date geraObjDate(Date dia, String hora) throws ParseException {

		SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy ");
		SimpleDateFormat sdfDataHora = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		return sdfDataHora.parse(sdfData.format(dia) + hora);
	}

	public static BigDecimal formataBigDecimal(String value) throws ApplicationServiceException {

		if (value == null || value.isEmpty()) {
			return null;
		}

		Locale brasil = new Locale("pt", "BR");
		DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(brasil));
		df.setParseBigDecimal(true);

		try {
			BigDecimal b1 = (BigDecimal) df.parse(value);

			return b1;

		} catch (Exception e) {
			throw new ApplicationServiceException(e);
		}
	}

	/**
	 * Testa se uma lista nao esta vazia.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isListaNaoVazia(Collection lista) {

		return lista != null && !lista.isEmpty();
	}

	/**
	 * Testa se um codigo nao e nulo, nem vazio, nem igual a "-1".
	 */
	public static boolean isCodigoValido(String codigo) {

		return codigo != null && !"".equals(codigo.trim()) && !"-1".equals(codigo.trim());
	}

	/**
	 * Formata uma string no formato do cep (999-99999)
	 */
	public static String formataCEP(String cep) {

		try {
			return mascaraGenerica(cep, "#####-###");

		} catch (Exception e) {
			return cep;
		}
	}

	/**
	 * Formata uma string no formato do telefone (9999-9999)
	 */
	public static String formataTelefone(String numFone) {

		try {
			if (numFone != null && numFone.length() == 8) {
				return mascaraGenerica(numFone, "####-####");

			} else if (numFone != null) {
				return numFone;

			} else {
				return "";
			}

		} catch (Exception e) {
			return numFone;
		}
	}

	/**
	 * Formata uma string no formato do telefone (999)9999-9999
	 * 
	 */
	public static String formataTelefone(String ddd, String numero) {

		try {

			String numeroCompleto = "";

			if (ddd != null && !ddd.isEmpty()) {
				numeroCompleto = "(".concat(ddd).concat(") ");
			}

			if (numero != null && !numero.isEmpty()) {
				numeroCompleto = numeroCompleto.concat(mascaraGenerica(numero, "####-####"));
			}

			return numeroCompleto;

		} catch (Exception e) {
			return null;
		}

	}

	public static String formatarProtocoloAAX(Integer protocolo) {

		String numProtocolo =  null;

		if (protocolo != null) {
			numProtocolo = protocolo.toString();
		}else{
			return "00.000.000-0";
		}

		return Util.formatString(numProtocolo, "##.###.###-#");
	}

	/**
	 * Tenta converter um numero pra inteiro. Se nao consegue, retorna nulo.
	 */
	public static Integer safeInteger(String numero) throws ApplicationServiceException {

		try {
			if (StringUtils.isNotBlank(numero) && isInteger(numero)) {
				return Integer.valueOf(numero);
			}

			return null;

		} catch (NumberFormatException ne) {
			throw new ApplicationServiceException(ne);
		}
	}

	/**
	 * Tenta converter um numero pra long. Se nao consegue, retorna nulo.
	 */
	public static Long safeLong(String numero) throws ApplicationServiceException {

		try {
			if (StringUtils.isNotBlank(numero) && isInteger(numero)) {
				return Long.valueOf(numero);
			}

			return null;

		} catch (NumberFormatException ne) {
			throw new ApplicationServiceException(ne);
		}
	}

	/**
	 * Tenta converter um numero pra Double. Se nao consegue, retorna nulo.
	 * 
	 */
	public static Double safeDouble(String numero) throws ApplicationServiceException {

		try {
			if (StringUtils.isNotBlank(numero)) {
				return Double.valueOf(numero);
			}

			return null;
		} catch (NumberFormatException ne) {
			throw new ApplicationServiceException(ne);
		}
	}

	/**
	 * Tenta converter um numero pra Boolean. Se nao consegue, retorna nulo.
	 * 
	 */
	public static Boolean safeBoolean(String value) throws ApplicationServiceException {

		try {
			if (StringUtils.isNotBlank(value)) {
				return Boolean.valueOf(value);
			}

			return null;
		} catch (NumberFormatException ne) {
			throw new ApplicationServiceException(ne);
		}
	}

	/**
	 * Verifica se uma String é um Inteiro.
	 * 
	 * @param String
	 *            com o valor
	 * @return boolean true se for Inteiro
	 */
	public static boolean isInteger(String str) {
		try {
			if (str.length() > 9)
				for (int i = 0; i < str.length(); ++i)
					Integer.parseInt(str.substring(i, i + 1));
			else
				Integer.parseInt(str);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Retorna um objeto Date a partir de uma String, de acordo com o padrao
	 * especificado.<br>
	 * 
	 * <pre>
	 * Exemplos de Padrao:
	 * "HH:mm"                   = 14:30
	 * "HH:mm:ss"                = 14:30:35
	 * "dd/MM/yyyy"              = 10/04/2008
	 * "dd/MM/yyyy HH:mm"        = 10/04/2008 14:30
	 * "dd 'de' MMMM 'de' yyyy"  = 07 de Abril de 2008
	 * "yyyy-MM-dd HH:mm:ss.SSS" = 2008-04-07 18:16:43.991
	 * </pre>
	 * 
	 * @param String
	 *            : data
	 * @param String
	 *            : padrao
	 * @return Date
	 * @throws ApplicationServiceException
	 * @throws Exception
	 */
	public static Date gerarObjetoDate(String data, String padrao) throws ApplicationServiceException {

		Date objeto = null;

		if (StringUtils.isBlank(data) || StringUtils.isBlank(padrao)) {
			return objeto;
		}
		try {
			objeto = new SimpleDateFormat(padrao).parse(data);

		} catch (Exception e) {
			throw new ApplicationServiceException(e);
		}
		return objeto;
	}

	/**
	 * Retorna um objeto Date no formato 'dd/MM/yyyy HH:mm' a partir de uma String.
	 */
	public static Date gerarObjetoDate(String data) throws ApplicationServiceException {

		Date objeto = null;
		if (StringUtils.isBlank(data)) {
			return objeto;
		}
		try {
			objeto = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(data);

		} catch (Exception e) {
			throw new ApplicationServiceException(e);
		}
		return objeto;
	}

	/**
	 * Converte um Set em Lista.<br>
	 * 
	 * @since 20/07/2007
	 * @param Set<T>
	 *            : set, caso nulo retorna list vazio.
	 * @return List<T>
	 * @throws Exception
	 * @review Digam
	 */
	public static <T> List<T> setToList(Set<T> set) {

		if (set == null || set.isEmpty()) {
			return new ArrayList<T>(0);
		}
		return new ArrayList<T>(set);
	}

	/**
	 * Converte uma Lista em Set.<br>
	 * 
	 * @since 20/07/2007
	 * @param List<T>
	 *            : list, caso nulo retorna set vazio.
	 * @return Set<T>
	 * @throws Exception
	 * @review Digam
	 */
	public static <T> Set<T> listToSet(List<T> list) {

		if (list == null || list.isEmpty()) {
			return new LinkedHashSet<T>(0);
		}
		return new LinkedHashSet<T>(list);
	}

	/**
	 * Cria uma lista com os objetos passados como parametros.
	 * 
	 * @param <T>
	 */
	@SafeVarargs
	public static <T> List<T> getList(T... objects) {

		List<T> lista = new ArrayList<T>(objects.length);

		for (T obj : objects) {
			lista.add(obj);
		}

		return lista;
	}

	/**
	 * Cria uma lista com os objetos passados como parametros.
	 * 
	 * @param <T>
	 */
	@SafeVarargs
	public static <T> Set<T> getSet(T... objects) {

		Set<T> set = new LinkedHashSet<T>(objects.length);

		for (T obj : objects) {
			set.add(obj);
		}

		return set;
	}

	/**
	 * Evita excessao por null pointer exception
	 */
	public static String safeToString(Object object) {

		if (object != null) {
			return object.toString();
		}
		return "";
	}

	/**
	 * Retorna TRUE se data 1 for posterior e data 2 (Despreza horas, minutos e
	 * segundos)
	 */
	public static Boolean dataMaior(Date data1, Date data2) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		return fmt.format(data1).compareTo(fmt.format(data2)) > 0;
	}

	/**
	 * Retorna TRUE se data 1 for anterior e data 2 (Despreza horas, minutos e
	 * segundos)
	 */
	public static Boolean dataMenor(Date data1, Date data2) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		return fmt.format(data1).compareTo(fmt.format(data2)) < 0;
	}

	/**
	 * Retorna TRUE se data1 for a mesma de data2 (Despreza horas, minutos e
	 * segundos).
	 */
	public static Boolean dataIgual(Date data1, Date data2) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		return fmt.format(data1).compareTo(fmt.format(data2)) == 0;
	}

	/**
	 * Retorna o tipo de extensao de um nome de arquivo.
	 * 
	 * @param nomeArquivo
	 *            : nome do arquivo
	 * @return Retorna uma string contendo a extensao do arquivo, por exemplo
	 *         ".pdf", ou uma string vazia, caso o nome do arquivo nao possua
	 *         extensao.
	 */
	public static String getExtensao(String nomeArquivo) {

		int lastIndex = nomeArquivo.lastIndexOf('.');
		int length = nomeArquivo.length();
		lastIndex = lastIndex == -1 ? length : lastIndex;
		String extensao = nomeArquivo.substring(lastIndex, length);

		return extensao;
	}

	/**
	 * Retorna o tamanho em bytes, KB ou MB, de acordo com parametro passado.
	 * 
	 * @param size
	 *            : tamanho em bytes a ser formatado.
	 */
	public static String formatarBytes(long size) {

		if (size < 1024) {
			return String.valueOf(size + " bytes");

		} else if (size >= 1024 && size < 1048576) {
			return String.valueOf(size / 1024 + " KB");

		} else {
			return String.valueOf(size / 1024 / 1024 + " MB");

		}
	}

	/**
	 * Retorna somente o DDD de um numero de telenone que esta no formato
	 * (999)9999-9999
	 * 
	 */
	public static String extrairDddTelefone(String telefone) {

		if (telefone == null || telefone.isEmpty() || telefone.length() < 8) {
			return null;
		}

		int a1 = telefone.indexOf('(');
		int a2 = telefone.indexOf(')');
		String ddd = telefone.substring(a1 + 1, a2);

		return ddd;
	}

	/**
	 * Remove os zeros a esquerda de uma String.
	 */
	public static String removerZerosEsquerda(String numero) {

		if (numero == null || numero.isEmpty()) {
			return null;
		}

		return numero.replaceAll("^0*", "");
	}

	/**
	 * Remove a formatacao de moeda eliminando os pontos e trocando as virgulas por
	 * pontos.
	 * 
	 * @return Retorna um Double com o valor ou null se ocorreu erro ao converter a
	 *         String.
	 * 
	 */
	public static Double removerFomatacaoMoeda(String valorFormatado) {

		try {
			String valorLimpo = valorFormatado.replace(".", "").replace(',', '.').replace("R$", "").trim();
			Double valor = Util.safeDouble(valorLimpo);

			return valor;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converte o StackTrace de uma execao para String.
	 * 
	 */
	public static String stackTraceToString(Throwable e) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		e.printStackTrace(pw);

		return sw.toString();
	}

	/**
	 * M&#233;todo respons&#225;vel por remover X dias da data informada.
	 * 
	 * @since 29/09/2014 09:16:07
	 * @param qtdeDias
	 *            : Integer
	 * @param data
	 *            : Date
	 * @return Date
	 */
	public static Date removeDias(Integer qtdeDias, Date data) {
		if (data == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) - qtdeDias));
		return calendar.getTime();
	}

	/**
	 * Verifica se a String &#233; igual a null ou "".
	 * 
	 */
	public static boolean stringIsNull(String s) {
		return (s == null || "".equals(s.trim()));
	}

	/**
	 * M&#233;todo respons&#225;vel por obter o ultimo dia util
	 * 
	 * @since 03/12/2014 14:21:54
	 * @param dataAtual
	 *            : Date
	 * @return Date
	 */
	public static Date obterUltimoDiaUtil(Date dataAtual) {
		Calendar data = Calendar.getInstance();
		data.setTime(dataAtual);

		do {
			data.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
		} while (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

		return data.getTime();
	}

	/**
	 * M&#233;todo respons&#225;vel por obter o substring da string informada caso a
	 * mesma seja maior que o indice final informado, retorna o subtring entre os
	 * indices senão, caso seja menor que o indice final informado, retorna do
	 * indice inicial em diante senão, retorna nulo
	 * 
	 * @since 24/04/2015 10:52:10
	 * @param indiceInicial
	 *            : int
	 * @param indiceFinal
	 *            : int
	 * @param dados
	 *            : String
	 * @return String
	 */
	public static String obterSubstring(Integer indiceInicial, Integer indiceFinal, String dados) {

		if (StringUtils.isNotBlank(dados)) {
			if (indiceFinal != null) {
				if (dados.length() >= indiceFinal) {
					return dados.substring(indiceInicial, indiceFinal).trim();

				} else if (dados.length() > indiceInicial) {
					return dados.substring(indiceInicial).trim();
				}

			} else {
				if (dados.length() >= indiceInicial) {
					return dados.substring(indiceInicial).trim();
				}
			}
		}

		return null;
	}

	public static String upperCaseString(String texto) {
		try {

			if (StringUtils.isNotBlank(texto)) {
				return texto.trim().toUpperCase();
			}

			return "";

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Método responsável por validar se é um cpf valido
	 *
	 * @param valor
	 * @return
	 *
	 * @rastreabilidade_requisito
	 */
	public static Boolean isCPFValid(String valor) {
		valor = Util.soNumeros(valor);
		if (Util.isBlank(valor) || valor.length() != 11) {
			return false;
		}
		String c = valor.substring(0, 9);
		String dv = valor.substring(9, 11);
		Integer d1 = 0;
		for (Integer i = 0; i < 9; i++) {
			d1 += Integer.parseInt("" + c.charAt(i)) * (10 - i);
		}

		if (d1 == 0) {
			return false;
		}
		d1 = 11 - (d1 % 11);
		if (d1 > 9) {
			d1 = 0;
		}
		if (Integer.parseInt("" + dv.charAt(0)) != d1) {
			return false;
		}
		d1 *= 2;
		for (Integer i = 0; i < 9; i++) {
			d1 += Integer.parseInt("" + c.charAt(i)) * (11 - i);
		}
		d1 = 11 - (d1 % 11);
		if (d1 > 9) {
			d1 = 0;
		}
		if (Integer.parseInt("" + dv.charAt(1)) != d1 || valor.equals("11111111111") || valor.equals("22222222222")
				|| valor.equals("33333333333") || valor.equals("44444444444") || valor.equals("55555555555")
				|| valor.equals("66666666666") || valor.equals("77777777777") || valor.equals("88888888888")
				|| valor.equals("99999999999")) {
			return false;
		}
		return true;
	}

	/**
	 * Método responsável por validar se é um cnpj valido
	 *
	 * @param cnpj
	 * @return
	 *
	 * @rastreabilidade_requisito
	 */
	public static boolean isCNPJValid(String cnpj) {
		try{
			cnpj = Util.soNumeros(cnpj);
			
		if (cnpj == null) {
			return false;
		}
		cnpj = Util.soNumeros(cnpj);
		if (cnpj.length() < 14) {
			return false;
		}
		
		int soma = 0, dig;
        String cnpj_calc = cnpj.substring(0, 12);
		
		char[] chr_cnpj = cnpj.toCharArray();
        /* Primeira parte */
        for (int i = 0; i < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
            }
        }
        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                dig);
        /* Segunda parte */
        soma = 0;
        for (int i = 0; i < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
            }
        }
        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                dig);
        return cnpj.equals(cnpj_calc);
    }
    catch (Exception e) {
        return false;
    }
}


	/**
	 * Método responsável por validar se um email é válido
	 *
	 * @param email
	 * @return boolean
	 */
	public static boolean isEmailValid(String email) {
		if ((email == null) || (email.trim().length() == 0)){
			return false;
		}

		String emailPattern = "\\b(^[_A-Za-z0-9-]{3,}(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
		Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * Método responsável por validar se a url é válida
	 *
	 * @param String
	 * @return boolean
	 */
	public static boolean isUrlValid(String url) {
		if ((url == null) || (url.trim().length() == 0)){
			return false;
		}

		String urlPattern = "\\b(^(http[s]?://|ftp://)?(www\\.)?[a-zA-Z0-9-\\.]+\\.(com|org|net|mil|edu|ca|co.uk|com.au|gov|br)$)\\b";
		Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(url);
		return matcher.matches();
	}

	/**
	 * Método responsável em testar se um vetor de qualquer tipo está nulo ou vazio.
	 * 
	 * @param array
	 *            : T[]
	 * @return Boolean
	 */
	public static <T> Boolean isEmptyArray(T[] array) {
		return (array == null || array.length == 0);
	}

	public static StringBuilder replacePrimerioAndPorWhere(StringBuilder builder) {
		if (builder == null) {
			return builder;
		}
		if (builder.toString().contains("AND")) {
			builder = builder.replace(builder.indexOf("AND"), builder.indexOf("AND") + 3, "WHERE");
		} else if (builder.toString().contains("and")) {
			builder = builder.replace(builder.indexOf("and"), builder.indexOf("and") + 3, "WHERE");
		}
		return builder;
	}



	/**
	 * 
	 * Método responsável por validar o DDD informado
	 *
	 * @param ddd
	 * @return
	 */
	public static boolean validarDDDTelefone(String ddd) {
		if (Util.isBlank(ddd)) {
			return false;
		}

		Integer num = Integer.valueOf(ddd);
		if (num < 11 || num > 99) {
			return false;
		}
		return true;
	}


	/**
	 * Verifica se a data passada como parametro e valida, somente no formato
	 * dd/MM/yyyy.<br>
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean validarData(String data) {
		boolean retorno = true;
		if (StringUtils.isBlank(data)) {
			retorno = false;
		} else {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			try {
				format.parse(data);
			} catch (ParseException pe) {
				retorno = false;
			}
		}
		return retorno;
	}

	/**
	 * Método responsável por comparar dois objetos
	 * @param o1 : Object
	 * @param o2 : Object
	 * @return Boolean
	 */
	public static Boolean comparar(Object o1, Object o2) {
		if(isNull(o1) && isNull(o2)){
			return Boolean.TRUE;
		}
		if(isNotNull(o1) && isNotNull(o2)){
			if(o1 instanceof String && o2 instanceof String) {
				return o1.toString().equalsIgnoreCase(o2.toString());
			}
			return o1.equals(o2);
		}
		return Boolean.FALSE;
	}

    /**
     * Método responsável por verificar se um CEP é válido
     * 
     * @param String
     * @return boolean
     */
    public static boolean isCepValido(String cep) {
        boolean isValid = false;
        if (StringUtils.isNotBlank(cep)) {
            isValid = Pattern.compile("[0-9]{2}.[0-9]{3}-[0-9]{3}").matcher(cep).matches();
        }
        return isValid;
    }
    
    /**
     * Método responsável por remover acentos
     * 
     * @param String
     * @return String
     */
	public static String removerAcentos(String string) {
		return cleanTextContent(Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
	}
	
	/**
	 * Método responsável por remover caracteres especiais
	 * @param s
	 * @return
	 */
	public static String removeCaracteresEspeciais(String s) {
		if (s != null) {
			s = s.replace(" ", "");
			s = s.replaceAll("\\p{P}", "");
		}
		return s;
	}
	
	/**
	 * Método responsável por formatar Coordenada 
	 *
	 * @paramString : coordenada
	 * @return String
	 */
	public static String formatarCoordenada(String coordenada) {
		coordenada = Util.soNumeros(coordenada);
		return formatString(coordenada, "##° ##'\'' ##\"");
	}
	
	public static String cleanTextContent(String text) 
    {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");
 
        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
         
        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");
 
        return text.trim();
    }

	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Long.parseLong(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static String formataStringSemAcento(String texto) throws ApplicationServiceException{
		if(StringUtils.isBlank(texto)){
			return texto;
		}
		try{
			String in  = "ÁÀÃÂÄÉÈÊËÍÌÎÏÓÒÕÔÖÚÙÛÜáàãâäéèêëíìîïóòõôöúùûüçÇ";
			String out = "AAAAAEEEEIIIIOOOOOUUUUaaaaaeeeeiiiiooooouuuucC";
			StringBuffer textoFormatado = new StringBuffer();
			for(int c=0; c< texto.length(); c++) {
				char ch = texto.charAt(c);
				int pos = in.indexOf(ch);
				String cat = (pos == -1) ? String.valueOf(ch) : out.substring(pos, pos+1);
				textoFormatado.append(cat);
			}
			return textoFormatado.toString();
		}catch (Exception e) {
			throw new ApplicationServiceException(e);
		}
	}	

}
