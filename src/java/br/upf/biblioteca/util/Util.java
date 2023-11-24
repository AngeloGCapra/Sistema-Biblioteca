package br.upf.biblioteca.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author
 */
public class Util implements Serializable {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Util.class.getName());

    /**
     * Formatar data para yyyy-MM-dd HH:mm:ss. Ex. 2021-07-06 16:01:00
     */
    public static final String FORMATO_RETORNO_DATAHORAAMERICANO = "yyyy-MM-dd HH:mm:ss";

    /**
     * Formatar data para dd/MM/yy HH:mm. Ex. 30/01/15 16:59
     */
    public static final String FORMATO_RETORNO_DATAHORA = "dd/MM/yy HH:mm";
    /**
     * Formatar data para dd/MM/yy HH:mm. Ex. 30/01/15 16:59
     */
    public static final String FORMATO_RETORNO_DATAHORASEGUNDO = "dd/MM/yy HH:mm:ss";
    /**
     * Formatar data para dd/MM/yyyy HH:mm Ex. 30/01/2015 16:59
     */
    public static final String FORMATO_RETORNO_DATAHORA_1 = "dd/MM/yyyy HH:mm";
    /**
     * Formatar data para dd/MM/yy Ex. 30/01/15
     */
    public static final String FORMATO_RETORNO_DATA = "dd/MM/yy";
    /**
     * Formatar data para dd/MM/yy Ex. 30/01/2015
     */
    public static final String FORMATO_RETORNO_DATACOMPLETA = "dd/MM/yyyy";
    /**
     * Formatar data para MMMM/yy Ex. janeiro/21
     */
    public static final String FORMATO_RETORNO_DATAMESANO = "MMMM/yy";
    /**
     * Formatar data para yyyy Ex. 2021
     */
    public static final String FORMATO_RETORNO_DATAANO = "yyyy";
    /**
     * Formatar data para ddMMyyyyy. Ex. 30012015
     */
    public static final String FORMATO_RETORNO_DATASEMSEPARADOR = "ddMMyyyy";
    /**
     * Formatar data para HH:mm. Ex. 16:59
     */
    public static final String FORMATO_RETORNO_HORA = "HH:mm";
    /**
     * Formatar data para HHmm. Ex. 1659
     */
    public static final String FORMATO_RETORNO_HORASEMSEPARADOR = "HHmm";

    //definindo o período inicial, com base na data atual, considerando uma data retroativa
    //exemplo: data atual 20/10/2016 a DATAINICIOULTIMOANO será 19/10/2015.
    private static final Calendar DATAINICIOULTIMO10ANO = buscarDataAPartirDataAtual(-3650);
    private static final Calendar DATAINICIOULTIMO5ANO = buscarDataAPartirDataAtual(-1825);
    private static final Calendar DATAINICIOULTIMO2ANO = buscarDataAPartirDataAtual(-730);
    private static final Calendar DATAINICIOULTIMOANO = buscarDataAPartirDataAtual(-365);
    private static final Calendar DATAINICIOULTIMOSEMESTRE = buscarDataAPartirDataAtual(-182);
    private static final Calendar DATAINICIOULTIMOTRIMESTRE = buscarDataAPartirDataAtual(-90);
    private static final Calendar DATAINICIOULTIMOMES = buscarDataAPartirDataAtual(-30);
    private static final Calendar DATAINICIOULTIMASEMANA = buscarDataAPartirDataAtual(-7);
    /**
     * Atributo que define o perído máximo de dias que podemos programar uma
     * notificação no servidor Onesignal. Atualmente foi detectado 30 dias como
     * período máximo.
     */
    public static final int ONESIGNAL_PERIODO_DIAS_LIMITE_PROGRAMA_NOTIFICACAO = 25;

    public Calendar getDATAINICIOULTIMO10ANO() {
        return DATAINICIOULTIMO10ANO;
    }

    public Calendar getDATAINICIOULTIMO5ANO() {
        return DATAINICIOULTIMO5ANO;
    }

    public Calendar getDATAINICIOULTIMO2ANO() {
        return DATAINICIOULTIMO2ANO;
    }

    public Calendar getDATAINICIOULTIMOANO() {
        return DATAINICIOULTIMOANO;
    }

    public Calendar getDATAINICIOULTIMOSEMESTRE() {
        return DATAINICIOULTIMOSEMESTRE;
    }

    public Calendar getDATAINICIOULTIMOTRIMESTRE() {
        return DATAINICIOULTIMOTRIMESTRE;
    }

    public Calendar getDATAINICIOULTIMOMES() {
        return DATAINICIOULTIMOMES;
    }

    public Calendar getDATAINICIOULTIMASEMANA() {
        return DATAINICIOULTIMASEMANA;
    }

    /**
     * Método utilizado para buscar data/hora atual do sistema.
     *
     * @return
     */
    public static Timestamp buscarDataHoraAtual() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Método utilizado para formatar uma data
     *
     * @param data
     * @param formatoRetorno
     * @return dataFormatada
     */
    public static String formatarData(Date data, String formatoRetorno) {
        String dataFormatada = null;
        if (data != null) {
            SimpleDateFormat formatas = new SimpleDateFormat(formatoRetorno);
            dataFormatada = formatas.format(data);
        }
        return dataFormatada;
    }

    /**
     * Método utilizado para buscar uma data passando a quantidade de dias
     * baseado em uma data base. Quando quizer a data anterior tem que passar um
     * valor inteiro negativo "-1", quando quizer uma data postarior, tem que
     * passar um valor inteiro positivo.
     *
     * @param data
     * @param quantDias
     * @return
     */
    public static Calendar buscarDataAPartirData(Calendar data, Integer quantDias) {
        //adicionando os dias para buscar a data...
        data.add(Calendar.DATE, quantDias);
        return data;
    }

    /**
     * Método utilizado para buscar uma data passando a quantidade de dias
     * baseado em uma data base. O retorno é definido como as 23:59:59.
     *
     * @param data
     * @param quantDias
     * @return
     */
    public static Calendar buscarDataAPartirDataParcela(Calendar data, Integer quantDias) {
        //adicionando os dias para buscar a data...
        data.add(Calendar.DATE, quantDias);
        //setando a hora, minuto e segundo para o último momento do dia
        data.set(Calendar.HOUR_OF_DAY, 23);
        data.set(Calendar.MINUTE, 59);
        data.set(Calendar.SECOND, 59);
        data.set(Calendar.MILLISECOND, 0);
        return data;
    }

    /**
     * Método utilizado para buscar uma data passando a quantidade de dias
     * baseado na data atual. Quando quizer a data anterior tem que passar um
     * valor inteiro negativo "-1", quando quizer uma data posterior, tem que
     * passar um valor inteiro positivo.
     *
     * @param quantDias
     * @return
     */
    public static Calendar buscarDataAPartirDataAtual(Integer quantDias) {
        //buscando data atual...
        Calendar data = Calendar.getInstance();
        //adicionando os dias para buscar a data...
        data.add(Calendar.DATE, quantDias);
        return data;
    }

    /**
     * Método utilizado para buscar uma data passando o dia de vencimento
     * baseado na data atual para daqui a um mês.
     *
     * @param diaVencimento
     * @return
     */
    public static Calendar buscarDataProximoMesParcela(Integer diaVencimento) {
        // buscando data atual...
        Calendar dataAtual = Calendar.getInstance();
        // adicionando um mês para buscar a data do próximo mês...
        dataAtual.add(Calendar.MONTH, 1);
        // obtendo o último dia do mês atual
        int ultimoDiaMesAtual = dataAtual.getActualMaximum(Calendar.DAY_OF_MONTH);
        //verificando se o dia de vencimento informado é maior que o último dia do mês atual
        if (diaVencimento > ultimoDiaMesAtual) {
            diaVencimento = ultimoDiaMesAtual;
        }
        //setando o dia de vencimento no próximo mês
        dataAtual.set(Calendar.DAY_OF_MONTH, diaVencimento);
        //setando a hora, minuto e segundo para o último momento do dia
        dataAtual.set(Calendar.HOUR_OF_DAY, 23);
        dataAtual.set(Calendar.MINUTE, 59);
        dataAtual.set(Calendar.SECOND, 59);
        dataAtual.set(Calendar.MILLISECOND, 0);
        return dataAtual;
    }

    /**
     * Método responsável por buscar uma data atual, utilizando como retorno um
     * Date
     *
     * @return
     */
    public static Date buscarDataAtual() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Método utilizado para passar uma data e uma quantidade de horas e retorna
     * uma nova data somanda a quantidade de horas...
     *
     * @param dataInformada
     * @param quantHoras
     * @return
     */
    public static Calendar buscarDataAPartirDataQuantHoras(Date dataInformada, Integer quantHoras) {
        Calendar data = new GregorianCalendar();
        data.setTime(dataInformada);
        //adicionando os dias para buscar a data...
        data.add(Calendar.HOUR, quantHoras);
        return data;
    }

    /**
     * Método utilizado para passar uma data e uma quantidade de horas e retorna
     * uma nova data somanda a quantidade de minutos...
     *
     * @param dataInformada
     * @param quantMinutos
     * @return
     */
    public static Calendar buscarDataAPartirDataQuantMinutos(Date dataInformada, Integer quantMinutos) {
        Calendar data = new GregorianCalendar();
        data.setTime(dataInformada);
        //adicionando os dias para buscar a data...
        data.add(Calendar.MINUTE, quantMinutos);
        return data;
    }

    //Convert Date to Calendar
    /**
     * Método responspável por converteruma data do Formato Date para Calendar
     *
     * @param date
     * @return
     */
    public static Calendar converteDateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //Convert Calendar to Date
    /**
     * Método responspável por converteruma data do Formato Calendar para Date
     *
     * @param date
     * @return
     */
    public static Date converteCalendarToDate(Calendar date) {
        Date data = date.getTime();
        return data;
    }

    public static Date converterLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Identifica o endereço IP remoto
     */
    public String getMeuIP() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return request.getRemoteAddr();
    }

    /**
     * Identifica o ID da sessão
     */
    public String getMinhaSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        return session.getId();
    }

    /**
     * Limpa os dados dos componentes de edição e de seus filhos,
     * recursivamente. Checa se o componente é instância de EditableValueHolder
     * e 'reseta' suas propriedades. lin:
     * http://www.rponte.com.br/2011/06/07/limpando-a-arvore-de-componentes/
     * <p>
     * Quando este método, por algum motivo, não funcionar, parta para
     * ignorância e limpe o componente assim:
     * <p>
     * <blockquote><pre>
     * component.getChildren().clear()
     * </pre></blockquote> :-)
     */
    public void cleanSubmittedValues(UIComponent component) {
        if (component instanceof EditableValueHolder) {
            EditableValueHolder evh = (EditableValueHolder) component;
            evh.setSubmittedValue(null);
            evh.setValue(null);
            evh.setLocalValueSet(false);
            evh.setValid(true);
        }
        if (component.getChildCount() > 0) {
            for (UIComponent child : component.getChildren()) {
                cleanSubmittedValues(child);
            }
        }
    }

    /**
     * Método utilizado para gerar código aleatório
     *
     * @param tamanho
     * @return
     */
    public static String gerarCodigoAleatorio(int tamanho) {
        Random ran = new Random();
        String[] letras = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String b = "";
        int num = tamanho;
        for (int i = 0; i < num; i++) {
            int a = ran.nextInt(letras.length);
            b += letras[a];
        }
        System.out.println("Senha gerada: " + b);
        return b;
    }

    /**
     * Método utilizado para gerar código aleatório
     *
     * @param tamanho
     * @return
     */
    public static String gerarCodigoAleatorioNumerico(int tamanho) {
        Random ran = new Random();
        String[] letras = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String b = "";
        int num = tamanho;
        for (int i = 0; i < num; i++) {
            int a = ran.nextInt(letras.length);
            b += letras[a];
        }
        //System.out.println("Senha gerada: " + b);
        return b;
    }

    /**
     * Método utilizado para calcular a idade
     *
     * @param dataNasc
     * @return idade
     */
    public static int calculaIdade(Date dataNasc) {
        // Data de hoje.
        Timestamp hoje = new Timestamp(System.currentTimeMillis());
        int diah = hoje.getDate();
        int mesh = hoje.getMonth() + 1;
        int anoh = hoje.getYear();

        int dian = dataNasc.getDate();
        int mesn = dataNasc.getMonth() + 1;
        int anon = dataNasc.getYear();

        // Idade.
        int idade;

        if (mesn < mesh || (mesn == mesh && dian <= diah)) {
            idade = anoh - anon;
        } else {
            idade = (anoh - anon) - 1;
        }
        return (idade);
    }

    /**
     * Método responsável por gerar um tokem a ser utilizado principalmente para
     * validar uma requisição WebService. O mesmo é renovado a cada dia e
     * utiliza criptografia MD5
     *
     * @param pesUsuario
     * @return
     */
    private static String gerarToken(String pesUsuario) {
        Date dataHoraAtual = new Date(Util.buscarDataHoraAtual().getTime());
        String dataSemSeparador = Util.formatarData(dataHoraAtual, Util.FORMATO_RETORNO_DATASEMSEPARADOR);
        //utiliza o pesUsuario + datahoraSemSeparador
        //exemplo:   usuario = 05513b17959e7f7c0f2b2f7e11d9e0f7 e dataAtual = 25012017
        //inclui tudo em uma string e criptografa, gerando um novo Hash, o qual se renova a cada dia.
        String token = Criptografia.criptografar(pesUsuario + dataSemSeparador);
        return token;
    }

    /**
     * método responsável por validar o tokenEsterno com token gerado pelo
     * server
     *
     * @param tokenExterno
     * @param pesUsuario
     * @return
     */
    public static boolean validarToken(String tokenExterno, String pesUsuario) {
        String tokenServer = gerarToken(pesUsuario);
        return tokenExterno.equals(tokenServer); //se token for válido (tokenExt == tokenServer), retorna true
    }

    /**
     * busca a mediana de um intervalo
     *
     * @param valInicio
     * @param valFim
     * @return
     */
    public static int buscarIndiceMedianaIntervalo(int valInicio, int valFim) {
        return (valInicio + valFim) / 2;
    }

    /*
     MEDIANA
     Mediana é uma medida de tendência central que indica exatamente o valor central de uma amostra de dados. 
     Exemplos:
     As notas de um aluno em um semestre da faculdade, colocadas em ordem crescente, 
     foram: 4,0; 4,0; 5,0; 7,0; 7,0. São cinco notas. A mediana é o valor que está no 
     centro da amostra, ou seja, 5,0. Podemos afirmar que 40% das notas estão acima de 5,0 e 40% estão abaixo de 5,0.

     A quantidade de hotéis 3 estrelas espalhados pelas cidades do litoral de um 
     determinado Estado é: 1, 2, 3, 3, 5, 7, 8, 10, 10, 10. Como a amostra possui dez valores e, portanto, 
     não há um valor central, calculamos a mediana tirando a média dos dois valores centrais: 
    
     MEDIANA = (5 + 7) / 2  => 12/2 = 6
    
     Assim, há exatamente 50% das cidades com mais de 6 hotéis três estrelas e 50% das cidades com menos de 6 hotéis três estrelas.

     Dessa forma, podemos resumir o cálculo da mediana da seguinte forma:

     - os valores da amostra devem ser colocados em ordem crescente ou decrescente;
     - se a quantidade de valores da amostra for ímpar, a mediana é o valor central da amostra. 
     Nesse caso, há a mesma quantidade de valores acima e abaixo desse valor;
     - se a quantidade de valores da amostra for par, é preciso tirar a média dos valores 
     centrais para calcular a mediana. Nesse caso, 50% dos valores da amostra estão 
     abaixo e 50% dos valores da amostra estão acima desse valor.
    
     Michele Viana Debus de França é licenciada em matemática pela USP e mestre em educação matemática pela PUC-SP.    
     */
    /**
     * Método responsável por definir a mediana de uma amostra de dados do tipo
     * BigDecimal
     *
     * @param listaAmostra
     * @return
     */
    public static BigDecimal definirMedianaBigDecimal(List<BigDecimal> listaAmostra) {
        BigDecimal mediana;
        int tamanhoAmostra = listaAmostra.size();
        int indiceMedianaImpar;
        int indiceValorCentralA = definirIndiceValorCentralA(tamanhoAmostra);
        int indiceValorCentralB = definirIndiceValorCentralB(indiceValorCentralA);

        for (int i = 0; i < listaAmostra.size(); i++) {
            //System.out.println(listaAmostra.get(i));
            Collections.sort(listaAmostra);	//ordenando a lista
        }

        //se a quantidade de valores da amostra for par, é preciso tirar a média dos valores centrais para calcular a mediana.
        if (tamanhoAmostra % 2 == 0) {//par
            mediana = (listaAmostra.get(indiceValorCentralA).add(listaAmostra.get(indiceValorCentralB))).divide(new BigDecimal(2));
        } else {//se a quantidade de valores da amostra for ímpar, a mediana é o valor central da amostra
            indiceMedianaImpar = tamanhoAmostra / 2; //define o indice da mediana
            mediana = listaAmostra.get(indiceMedianaImpar);
        }
        return mediana;
    }

    /**
     * Método responsável por definir a mediana de uma amostra de dados do tipo
     * Integer
     *
     * @param listaAmostra
     * @return
     */
    public static Integer definirMedianaInteger(List<Integer> listaAmostra) {
        Integer mediana;
        int tamanhoAmostra = listaAmostra.size();
        int indiceMedianaImpar;
        int indiceValorCentralA = definirIndiceValorCentralA(tamanhoAmostra);
        int indiceValorCentralB = definirIndiceValorCentralB(indiceValorCentralA);

        for (int i = 0; i < listaAmostra.size(); i++) {
            //  System.out.println(listaAmostra.get(i));
            Collections.sort(listaAmostra);	//ordenando a amostra
        }

        //se a quantidade de valores da amostra for par, é preciso tirar a média dos valores centrais para calcular a mediana.
        if (tamanhoAmostra % 2 == 0) { //par            
            mediana = (listaAmostra.get(indiceValorCentralA) + listaAmostra.get(indiceValorCentralB)) / 2;
        } else { //se a quantidade de valores da amostra for ímpar, a mediana é o valor central da amostra
            indiceMedianaImpar = tamanhoAmostra / 2; //impar
            mediana = listaAmostra.get(indiceMedianaImpar);
        }
        return mediana;
    }

    /**
     * Define o indice do valor central A
     *
     * @param tamanhoAmostra
     * @return
     */
    public static int definirIndiceValorCentralA(int tamanhoAmostra) {
        return (tamanhoAmostra / 2) - 1;
    }

    /**
     * Define o indice do valor central A
     *
     * @param indiceValorCentralA
     * @return
     */
    public static int definirIndiceValorCentralB(int indiceValorCentralA) {
        return indiceValorCentralA + 1;
    }

    /**
     * Método que recebe um texto contendo números separados por ponto-e-virgula
     * ex.:"5,10,25,13,4,1500" e retorna um array de inteiro
     * [5,10,25,13,4,1500].
     *
     * @param texto
     * @return
     */
    public static Integer[] parserStringToArrayInteger(String texto) {
        // texto = "5,10,25,13,4,1500";
        String[] textoSeparado = texto.split(",");
        Integer[] arrayInteger = new Integer[textoSeparado.length];

        for (int i = 0; i < textoSeparado.length; i++) {
            arrayInteger[i] = Integer.parseInt(textoSeparado[i]);
        }
        //System.out.println(Arrays.toString(arrayInteger));
        return arrayInteger;
    }

    /**
     * Método que recebe um texto contendo números separados por ponto-e-virgula
     * ex.:"5,10,25,13,4,1500" e retorna um array de inteiro
     * [5,10,25,13,4,1500].
     *
     * @param texto
     * @return
     */
    public static Double[] parserStringToArrayDouble(String texto) {
        // texto = "5,10,25,13,4,1500";
        String[] textoSeparado = texto.split(",");
        Double[] arrayDouble = new Double[textoSeparado.length];

        for (int i = 0; i < textoSeparado.length; i++) {
            arrayDouble[i] = Double.parseDouble(textoSeparado[i]);
        }
        //System.out.println(Arrays.toString(arrayInteger));
        return arrayDouble;
    }

    /**
     * Converter uma data para o formato Long
     *
     * @param data
     * @return
     */
    public static Long parserDateToLong(Date data) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTimeInMillis(data.getTime());
        return dataCalendar.getTimeInMillis();
    }

    /**
     * Método responsável por receber um long e converter em Calendar
     *
     * @param data
     * @return
     */
    public static Date parserLongToDate(Long data) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTimeInMillis(data);
        return dataCalendar.getTime();
    }

    /**
     * Método responsável por tratar uma data, retornando somente dd/MM/aaaa A
     * Hora, minuto e segundo é zerada.
     *
     * @param data
     * @return
     */
    public static Date tratarDatanascimentoDate(Date data) {
        Date dataNova = new Date();
        dataNova.setDate(data.getDate());
        dataNova.setYear(data.getYear());
        dataNova.setMonth(data.getMonth());
        dataNova.setSeconds(0);
        dataNova.setHours(0);
        dataNova.setMinutes(0);
        return dataNova;
    }

    /**
     * Método responsável por tratar uma data, retornando somente dd/MM/aaaa A
     * Hora, minuto e segundo é zerada.
     *
     * @param dataLong
     * @return
     */
    public static Date tratarDatanascimentoLong(Long dataLong) {
        Date data = new Date(dataLong);
        return tratarDatanascimentoDate(data);
    }

    public static String removerAspasString(String token) {
        if (token != null && token.length() > 0) {
            token = token.replaceAll("\"", "");
        }
        return token;
    }

    /**
     * Método responsável por converter um Calendar para um LocalDataTime
     *
     * @param calendar
     * @return
     */
    public static LocalDateTime calendarToLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    /**
     * Método responsável por converter um Date para um LocalDataTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Converter uma string no formato "dd/MM/YYYY" em date.
     *
     * @param dataString
     * @return
     */
    public Date converterStringToDate(String dataString) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = formato.parse(dataString);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     * Método responsável por receber um valor Double, converter em BigDecimal e
     * definir o numero de casas decimais.
     *
     * @param valor
     * @param casasDecimais
     * @return
     */
    public static BigDecimal parserDoubleToBigdecimalDefineDecimal(Double valor, int casasDecimais) {
        BigDecimal valorReturn = new BigDecimal(valor);
        if (valor > 0) {
            valorReturn = valorReturn.setScale(casasDecimais, BigDecimal.ROUND_HALF_EVEN);
        }
        return valorReturn;

    }

    /**
     * método responsável por converter uma string no formato Base64 para uma
     * Array de Byte. Não será preciso utilizar, pois o Jersey faz a conversão
     * automaticamente.
     *
     * @param stringBase64
     * @return
     */
    private byte[] convertStringBase64ToArrayByte(String stringBase64) {
        return Base64.getDecoder().decode(stringBase64);
    }

    /**
     * método responsável por converter um Array de Byte para uma String Base64.
     * Não será preciso utilizar, pois o Jersey faz a conversão automaticamente.
     *
     * @param stringBase64
     * @return
     */
    private String convertArrayByteToStringBase64(byte[] arrayByte) {
        return Base64.getEncoder().encodeToString(arrayByte);
    }

    public static void main(String[] args) {
//        BigDecimal pontos = new BigDecimal(10);
//        BigDecimal amostragem = new BigDecimal(1);
//        BigDecimal ecgIntervalo = pontos.divide(amostragem, 2, RoundingMode.HALF_UP);
//        System.out.println(ecgIntervalo);
//        System.out.println(Calendar.getInstance().getTime());
//        System.out.println(Calendar.getInstance().getTimeInMillis());
//        Date data = Calendar.getInstance().getTime();
//        System.out.println(Calendar.getInstance().getTimeInMillis());
//        System.out.println(tratarDatanascimentoLong(Calendar.getInstance().getTimeInMillis()));
//        System.out.println(data);
//        System.out.println(tratarDatanascimentoDate(data));
    }

    public static String converteJsonEmString(BufferedReader buffereReader) throws IOException {
        String resposta, jsonEmString = "";
        while ((resposta = buffereReader.readLine()) != null) {
            jsonEmString += resposta;
        }
        return jsonEmString;
    }

    public static String formatCPF(String cpf) {
        // Remover todos os caracteres não numéricos do CPF
        String cleanedCPF = cpf.replaceAll("[^0-9]", "");

        // Adicionar a máscara de CPF (###.###.###-##)
        String formattedCPF = cleanedCPF.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

        return formattedCPF;
    }

    /**
     * Método utilizado para retornar o dia da semana passando uma data como
     * string no formado dd/MM/yyyy ex. 07/03/2017
     *
     * @param date
     * @return
     */
    public static String buscarDiaSemana(String date) { //ex 07/03/2017
        String dayWeek = "---";
        GregorianCalendar gc = new GregorianCalendar();
        try {
            gc.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(date));
            switch (gc.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    dayWeek = "Domingo";
                    break;
                case Calendar.MONDAY:
                    dayWeek = "Segunda-feira";
                    break;
                case Calendar.TUESDAY:
                    dayWeek = "Terça-feira";
                    break;
                case Calendar.WEDNESDAY:
                    dayWeek = "Quarta-feira";
                    break;
                case Calendar.THURSDAY:
                    dayWeek = "Quinta-feira";
                    break;
                case Calendar.FRIDAY:
                    dayWeek = "Sexta-feira";
                    break;
                case Calendar.SATURDAY:
                    dayWeek = "Sábado";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayWeek;
    }

    /**
     * Arredonda o valor para duas casas decimais Ex: Valor: 123.456789
     * Retornará: 123.46
     *
     * @param valor
     * @return
     */
    public static Double formatDoubleDuasCasasDecimais(Double valor) {
        if (valor == null) {
            return null; // Lida com valores nulos, se necessário
        }

        // Arredonda o valor para duas casas decimais
        double roundedValue = Math.round(valor * 100.0) / 100.0;

        return roundedValue;
    }

    /**
     * Arredonda o valor completamente Ex: Valor: 123.456789 Retornará: 123
     *
     * @param valor
     * @return
     */
    public static Double formatDoubleSemCasasDecimais(Double valor) {
        if (valor == null) {
            return null; // Lida com valores nulos, se necessário
        }

        // Arredonda o valor
        long valorLong = Math.round(valor);
        double roundedValue = (double) valorLong; // Convertendo de long para Double   

        return roundedValue;
    }

    /**
     * Arredonda o valor para uma casa decimal
     *
     * @param valor
     * @return
     */
    public static BigDecimal formatBigDecimalUmaCasaDecimal(BigDecimal valor) {
        if (valor == null) {
            return null;
        }
        BigDecimal roundedValue = valor.setScale(1, RoundingMode.HALF_UP);
        return roundedValue;
    }

}
