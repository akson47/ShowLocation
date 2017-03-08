package com.example.showlocation.tools;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.showlocation.R;


public class PanelSlideViews implements View.OnTouchListener {


    public enum SWIPE_DIRECTION {
        BOTTOM_TO_UP, UP_TO_BOTTOM;
    }

    private float startX = 0;
    private float startY = 0;
    private float moveX = 0;
    private float moveY = 0;
    private float endX = 0;
    private float endY = 0;

    private float expandedStateHeight;
    private float collapsedStateHeight;


    private SWIPE_DIRECTION direction;
    private final int CLICK_ACTION_THRESHHOLD;
    public View controlView;
    public View contentView;
    private boolean isExpanded;
    private PanelSlideListener panelSlideListener;


    public PanelSlideViews(AppCompatActivity activity, View controlView, View contentView, float expandedStateHeight, float collapsedStateHeight, SWIPE_DIRECTION direction) {
        this.controlView = controlView;
        this.contentView = contentView;
        this.expandedStateHeight = expandedStateHeight;

        this.collapsedStateHeight = collapsedStateHeight;
        this.direction = direction;


        CLICK_ACTION_THRESHHOLD = Helper.convertDpToPx(activity, R.dimen.click_dp);



    }

    public void active() {
        controlView.setOnTouchListener(this);
    }

    public void inActive() {
        controlView.setOnTouchListener(null);
    }

    private ValueAnimator slideAnimator(int start, int end, final View summary) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = summary.getLayoutParams();
                layoutParams.height = value;
                summary.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public void collapse() {
        int currentHeight = contentView.getHeight();

        ValueAnimator mAnimator = slideAnimator(currentHeight, (int) collapsedStateHeight, contentView);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {

                if (panelSlideListener != null)
                    panelSlideListener.onCollapsed();
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    public void expand() {
        int currentHeight = contentView.getHeight();
        ValueAnimator mAnimator = slideAnimator(currentHeight, (int) expandedStateHeight, contentView);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                if (panelSlideListener != null)
                    panelSlideListener.onExpanded();
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                moveX = event.getX();
                moveY = event.getY();

                if (isAClick(startX, moveX, startY, moveY)) {

                    return true;
                }

                ViewGroup.LayoutParams params = contentView.getLayoutParams();

                float difY;
                if (direction == SWIPE_DIRECTION.BOTTOM_TO_UP) {
                    difY = startY - moveY;
                } else {
                    difY = moveY - startY;
                }


                if (difY > 0) {
                    if (params.height + difY < expandedStateHeight) {
                        params.height = (int) (params.height + difY);
                    } else {
                        params.height = (int) expandedStateHeight;
                    }
                    contentView.setLayoutParams(params);
                } else {
                    if (params.height + difY > 0) {
                        params.height = (int) (params.height + difY);
                    } else {
                        params.height = 0;
                    }
                    contentView.setLayoutParams(params);
                }
                contentView.setLayoutParams(params);


                break;
            case MotionEvent.ACTION_UP: {
                endX = event.getX();
                endY = event.getY();
                if (isAClick(startX, endX, startY, endY)) {


                    if (isExpanded) {
                        collapse();

                    } else {
                        expand();
                    }
                    isExpanded = !isExpanded;

                } else {
                    params = contentView.getLayoutParams();
                    if (params.height >= expandedStateHeight / 2) {
                        expand();
                        isExpanded = true;
                    } else {
                        collapse();
                        isExpanded = false;
                    }
                }


                break;
            }
        }
        return true;
    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);

        return true;
    }

    public void setPanelSlideListener(PanelSlideListener panelSlideListener) {
        this.panelSlideListener = panelSlideListener;
    }

    public interface PanelSlideListener {
        void onExpanded();

        void onCollapsed();
    }
}
