package vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass;

import net.kyori.adventure.text.Component;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractAnimationTitle extends AbstractTitle{
    {
        setType(TitleType.TITLE);
    }
    private List<Component> listFrames;
    private int delayBetweenFrame;
    public void setListFrames(List<?> listFrames){
        List<Component> outList = new ArrayList<>();
        for (Object object : listFrames){
            if (object instanceof Component)
                outList.add((Component) object);
            else if (object instanceof String)
                outList.add(Utils.convertToComponent((String) object));
        }
        this.listFrames = outList;
    }

    public void setDelayBetweenFrame(int delayBetweenFrame) {
        this.delayBetweenFrame = delayBetweenFrame;
    }
    public List<Component> getListFrames() {
        return listFrames;
    }
    public int getDelayBetweenFrame() {
        return delayBetweenFrame;
    }
}
