/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.controller;

import br.unioeste.jgoose.model.TokensUseCase;
import br.unioeste.jgoose.model.UCUseCase;


/**
 *
 * @author Victor Augusto Pozzan
 */
public class UCController {
    private static TokensUseCase tokensUC;

    
    public static TokensUseCase getTokensUC(){
        return tokensUC;
    }
    
    public static void setTokensUC(TokensUseCase tokensUC){
        UCController.tokensUC = tokensUC;
    }
    
}
