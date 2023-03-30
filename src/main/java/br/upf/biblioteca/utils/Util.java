package br.upf.biblioteca.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
	
	// Formatar data para dd/MM/yyyy Ex. 28/03/2023
	public static final String FORMATO_RETORNO_DATA = "dd/MM/yyyy";
	// Formatar data para dd/MM/yyyy Ex. 28/03/2023 00:00:00
	public static final String FORMATO_RETORNO_DATAHMS = "dd/MM/yyyy HH:mm:ss";
	
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
     * Método utilizado para buscar uma data passando a quantidade de dias
     * baseado na data atual. Quando for necessário buscar a data anterior a 
     * data atual, é preciso passar um valor inteiro negativo "-1", e quando
     * for necessário buscar uma data posterior, é preciso passar um valor 
     * inteiro positivo.
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
     * Método utilizado para formatar uma data
     *
     * @param data
     * @param formatoRetorno
     * @return dataFormatada
     */
    public static String formatarData(Date data, String formatoRetorno) {
        String dataFormatada = null;
        if (data != null) {
            SimpleDateFormat formater = new SimpleDateFormat(formatoRetorno);
            dataFormatada = formater.format(data);
        }
        return dataFormatada;
    }
    
    /**
     * Método para formatar e converter uma data
     *
     * @param data
     * @param formatoDaData
     * @return dataConvertida
     */
    public static Date formatAndConvert(Date data, String formatoDaData) {
        String dataFormatada = null;
        Date dataConvertida = null;
        if (data != null) {
            SimpleDateFormat formater = new SimpleDateFormat(formatoDaData);
            dataFormatada = formater.format(data);
            dataConvertida = converterStringToDate(dataFormatada);
        }
        return dataConvertida;
    }
    
    /**
     * Converter uma string no formato "dd/MM/YYYY" em date.
     *
     * @param dataString
     * @return
     */
    public static Date converterStringToDate(String dataString) {
        SimpleDateFormat formato = new SimpleDateFormat(Util.FORMATO_RETORNO_DATA);
        Date data = null;
        try {
            data = formato.parse(dataString);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

}
