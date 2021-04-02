package com.jaeheonshim.tetris.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.jaeheonshim.tetris.TetrisGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                Window.enableScrolling(false);
                Window.setMargin("0");
                Window.addResizeHandler(new ResizeListener());
                return new GwtApplicationConfiguration(Window.getClientWidth(), Window.getClientHeight());
                // Fixed size application:
                //return new GwtApplicationConfiguration(480, 320);
        }

        class ResizeListener implements ResizeHandler {
                @Override
                public void onResize(ResizeEvent event) {
                        if(Gdx.graphics.isFullscreen()) return;
                        getRootPanel().setWidth(event.getWidth() + "px");
                        getRootPanel().setHeight(event.getHeight() + "px");
                        getApplicationListener().resize(event.getWidth(), event.getHeight());
                        Gdx.graphics.setWindowedMode(event.getWidth(), event.getHeight());
                }
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new TetrisGame();
        }

        @Override
        public Preloader.PreloaderCallback getPreloaderCallback() {
                return createPreloaderPanel(GWT.getHostPageBaseURL() + "assets/jaeheonshim.png");
        }

        @Override
        protected void adjustMeterPanel(Panel meterPanel, Style meterStyle) {
                meterPanel.addStyleName("nostripes");
                meterStyle.setProperty("backgroundColor", "#FFFFFF");
        }
}