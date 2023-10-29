package com.y.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/11 12:11
 */
public class BackgroundComponent extends Component {
    //会自动注入
    private SayHiComponent sc;

    /**
     * 当组件被添加到实体的时候会被调用
     */
    @Override
    public void onAdded() {

        ViewComponent viewComponent = entity.getViewComponent();
        viewComponent.addOnClickHandler(e -> {
            Texture node = (Texture) viewComponent.getChildren().get(0);
            node.dispose();
            viewComponent.clearChildren();
            viewComponent.addChild(texture("bg.png", 535 / 5.0, 800 / 5.0).multiplyColor(FXGLMath.randomColor()));
            //entity.getComponent(SayHiComponent.class).syaHi();
            sc.syaHi();
        });
    }

    @Override
    public void onUpdate(double tpf) {
        System.out.println("更新中...");
    }
}
