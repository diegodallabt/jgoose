/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unioeste.jgoose.e4j.actions;

/**
 *
 * @author Diego
 */
public class Tupla<A, B> {
    
    public final A primeiro;
    public final B segundo;

    public Tupla(A primeiro, B segundo) {
        this.primeiro = primeiro;
        this.segundo = segundo;
    }

}
