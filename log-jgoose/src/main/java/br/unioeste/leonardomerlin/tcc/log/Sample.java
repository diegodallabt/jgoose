package br.unioeste.leonardomerlin.tcc.log;

import org.apache.log4j.Logger;

public class Sample {

    public static Logger console = Logger.getLogger("Console");
    public static Logger error = Logger.getLogger("txt");

    public static void main(String[] args) {
        console.info("info");
        error.debug("debug");
    }
}
