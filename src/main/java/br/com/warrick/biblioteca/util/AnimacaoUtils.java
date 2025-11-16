package br.com.warrick.biblioteca.util;

import br.com.warrick.biblioteca.view.screens.livro.*;
import java.awt.Dimension;
import java.awt.Point;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.callback.TimelineCallbackAdapter;

/**
 * Utilitário para gerenciar animações na interface
 * Fornece efeitos visuais como fade, slide e rotação
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class AnimacaoUtils {

    /* ========================================== ANIMAÇÕES DE LIVRO ========================================== */
    
    /* ========================================== MÉTODOS AUXILIARES ========================================== */
    
    /**
     * Anima a saída de um livro
     * 
     * @param livro Livro a ser animado
     * @param origem Ponto inicial
     * @param destino Ponto final
     * @param onComplete Callback ao terminar
     */
    public static void animarSaida(Livro livro, Point origem, Point destino, Runnable onComplete) {
        // Cria uma nova linha do tempo para a animação
        Timeline anim = new Timeline(livro);
        
        // Configura as propriedades a serem interpoladas
        anim.addPropertyToInterpolate("location", origem, destino);
        anim.addPropertyToInterpolate("size", new Dimension(20, 100), new Dimension(170, 220));

        // Variável para controle de rotação
        float[] rot = {0};
        
        // Adiciona rotação à animação
        anim.addPropertyToInterpolate("rotation", 0f, 90f);
        
        // Define a duração da animação em milissegundos
        anim.setDuration(800);

        // Adiciona callbacks para controle da animação
        anim.addCallback(new TimelineCallbackAdapter() {
            @Override
            public void onTimelinePulse(float durationFraction, float timelinePosition) {
                // Quando a animação passar da metade, mostra a capa do livro
                if (timelinePosition > 0.5f) {
//                    livro.mostrarCapa();
                }
            }

            @Override
            public void onTimelineStateChanged(org.pushingpixels.trident.Timeline.TimelineState oldState,
                    org.pushingpixels.trident.Timeline.TimelineState newState,
                    float durationFraction, float timelinePosition) {
                if (newState == org.pushingpixels.trident.Timeline.TimelineState.DONE) {
                    onComplete.run();
                }
            }
        });

        anim.play();
    }

    /**
     * Anima o retorno de um livro
     * 
     * @param livro Livro a ser animado
     * @param destino Ponto final
     * @param onComplete Callback ao terminar
     */
    public static void animarVolta(Livro livro, Point destino, Runnable onComplete) {
        // Mostra a lombada do livro antes de iniciar a animação
//        livro.mostrarLombada();
        
        // Cria uma nova linha do tempo para a animação
        Timeline anim = new Timeline(livro);
        
        // Configura as propriedades a serem interpoladas
        anim.addPropertyToInterpolate("location", livro.getLocation(), destino);
        anim.addPropertyToInterpolate("size", new Dimension(170, 220), new Dimension(20, 100));
        
        // Define a duração da animação em milissegundos
        anim.setDuration(600);
        
        // Adiciona callback para ser executado quando a animação terminar
        anim.addCallback(new TimelineCallbackAdapter() {
            @Override
            public void onTimelineStateChanged(org.pushingpixels.trident.Timeline.TimelineState oldState,
                    org.pushingpixels.trident.Timeline.TimelineState newState,
                    float durationFraction, float timelinePosition) {
                // Quando a animação for concluída, executa o callback
                if (newState == org.pushingpixels.trident.Timeline.TimelineState.DONE) {
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            }
        });
        
        // Inicia a animação
        anim.play();
    }
}
