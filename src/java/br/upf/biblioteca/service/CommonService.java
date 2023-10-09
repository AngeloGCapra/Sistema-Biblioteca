/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.biblioteca.service;

/**
 *
 * @author jeang
 */
public class CommonService {

    /**
     * Método que busca definir se recarrega ou não a lista de itens, 
     * Caso isReload=true, força o reload, caso contrario não. Essa implementação
     * foi realizada para antender a demanda de atualização em tempo real
     * reailzadas a partir das requisições REST.
     *
     * @param isItems
     * @param isReload
     * @return
     */
    public boolean reloadItems(boolean isItems, boolean isReload) {
        return isItems || isReload;
    }

}
