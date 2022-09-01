package br.com.dxc.cards;

import br.com.dxc.cards.MyRouteBuilder;

public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        MyRouteBuilder myRouteBuilder = new MyRouteBuilder();
        myRouteBuilder.configure();
    }

}

