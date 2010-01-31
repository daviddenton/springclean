package springclean.core.generate.data;

import java.util.List;

public class SetterInjectedBean {

    private List aList;
    private NoDependencyBean noDependencyBean;
    private AnImportedBean anImportedBean;
    private AnonymousBean anonymousBean;
    private ConstructorInjectedBean aConstructorInjectedBean;
    private String shortCutValue;
    private int expandedValue;

    public void setAnImportedBean(AnImportedBean anImportedBean) {
        this.anImportedBean = anImportedBean;
    }

    public void setAList(List aList) {
        this.aList = aList;
    }

    public void setAnonymousBean(AnonymousBean anonymousBean) {
        this.anonymousBean = anonymousBean;
    }

    public void setNoDependencyBean(NoDependencyBean noDependencyBean) {
        this.noDependencyBean = noDependencyBean;
    }

    public void setShortcutRef(ConstructorInjectedBean aConstructorInjectedBean) {
        this.aConstructorInjectedBean = aConstructorInjectedBean;
    }

    public void setShortCutValue(String shortCutValue) {
        this.shortCutValue = shortCutValue;
    }

    public void setExpandedValue(int expandedValue) {
        this.expandedValue = expandedValue;
    }

    public void start() {

    }
}