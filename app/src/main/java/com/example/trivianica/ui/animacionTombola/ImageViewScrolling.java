package com.example.trivianica.ui.animacionTombola;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trivianica.model.claseRecurso.cR;
import com.example.trivianica.R;

public class ImageViewScrolling extends FrameLayout
{
    int ANIMACION_DUR = 150;
    int valorAnterior   = 0;

    public static int fotogramaGuardado = -1;

    ImageView ImagenActual;
    ImageView ImagenProxima;
    TextView  Titulo;
    InterfazEventoTerminado eventEnd;

    public void setEventEnd(InterfazEventoTerminado eventEnd)
    {this.eventEnd = eventEnd;}

    public ImageViewScrolling(Context context)
    {
        super(context);
        init(context);
    }

    public ImageViewScrolling(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this);

        ImagenActual  = findViewById(R.id.ImagenActual);
        ImagenProxima = findViewById(R.id.ImagenProxima);
        Titulo        = findViewById(R.id.Titulo);

        ImagenActual.setTranslationY(getHeight());
    }
    public void setAnimacion(int imagen, int GirosConteo, TextView Titulo, boolean validador)
    {
        if(validador)
        {
            valorAnterior = fotogramaGuardado;
        }

        ImagenActual.animate().translationY(-getHeight()).setDuration(ANIMACION_DUR).start();
        ImagenProxima.setTranslationX(ImagenProxima.getHeight());

        ImagenProxima.animate().translationY(0).setDuration(ANIMACION_DUR)
                .setListener(
                new Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        int valor = valorAnterior%6;
                        setImagen(ImagenActual, valor);
                        setTitulo(Titulo, valor );
                        ImagenActual.setTranslationY(0);
                        if(valorAnterior != GirosConteo)
                        {
                            valorAnterior++;
                            fotogramaGuardado = valorAnterior;
                            setAnimacion(imagen, GirosConteo, Titulo, false);
                        }
                        else
                        {
                            valorAnterior = 0;
                            setImagen(ImagenActual, imagen);
                            setTitulo(Titulo, imagen);
                            fotogramaGuardado = -1;
                            eventEnd.eventEnd();
                        }
                    }
                    @Override
                    public void onAnimationCancel(Animator animation){}
                    @Override
                    public void onAnimationRepeat(Animator animation){}
                    @Override
                    public void onAnimationStart(Animator animation){}
                });
    }

    private void setTitulo(TextView Titulo, int categoriaId)
    {Titulo.setText(cR.Valores.getNombreCategoria(categoriaId));}

    private void setImagen(ImageView Imagen, int categoriaId)
    {
             if(categoriaId == cR.Valores.getARTE()       ) Imagen.setImageResource(R.drawable.ic_arte        );
        else if(categoriaId == cR.Valores.getDEPORTE()    ) Imagen.setImageResource(R.drawable.ic_deportes    );
        else if(categoriaId == cR.Valores.getGASTRONOMIA()) Imagen.setImageResource(R.drawable.ic_gastronomia );
        else if(categoriaId == cR.Valores.getGEOGRAFIA()  ) Imagen.setImageResource(R.drawable.ic_geografia   );
        else if(categoriaId == cR.Valores.getHISTORIA()   ) Imagen.setImageResource(R.drawable.ic_historia    );
        else if(categoriaId == cR.Valores.getTRADICION()  ) Imagen.setImageResource(R.drawable.ic_tradicion   );

        Imagen.setTag(categoriaId);
    }
}