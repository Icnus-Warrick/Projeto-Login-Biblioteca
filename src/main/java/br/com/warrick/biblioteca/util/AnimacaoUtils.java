package br.com.warrick.biblioteca.util;

import br.com.warrick.biblioteca.Livro.Livro;
import java.awt.Dimension;
import java.awt.Point;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.callback.TimelineCallbackAdapter;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 02/11/2025
 */

public class AnimacaoUtils {

    public static void animarSaida(Livro livro, Point origem, Point destino, Runnable onComplete) {
        Timeline anim = new Timeline(livro);
        anim.addPropertyToInterpolate("location", origem, destino);
        anim.addPropertyToInterpolate("size", new Dimension(20, 100), new Dimension(170, 220));

        float[] rot = {0};
        anim.addPropertyToInterpolate("rotation", 0f, 90f);
        anim.setDuration(800);

        anim.addCallback(new TimelineCallbackAdapter() {
            @Override
            public void onTimelinePulse(float durationFraction, float timelinePosition) {
                if (timelinePosition > 0.5f) {
                    livro.mostrarCapa();
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

    public static void animarVolta(Livro livro, Point destino, Runnable onComplete) {
        livro.mostrarLombada();
        Timeline anim = new Timeline(livro);
        anim.addPropertyToInterpolate("location", livro.getLocation(), destino);
        anim.addPropertyToInterpolate("size", new Dimension(170, 220), new Dimension(20, 100));
        anim.setDuration(600);
        anim.addCallback(new TimelineCallbackAdapter() {
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
}
