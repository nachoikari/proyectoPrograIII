package Utils;

public class SelectionModel {
    private static SelectionModel selection;
    private int option;
    
    private SelectionModel() {}
    
    public static SelectionModel getInstance() {
        if (selection == null) {
            selection = new SelectionModel();
        }
        return selection;
    }
    
    public void setOption(int _option){
        option = _option;
    }
    
    public int getOption(){
        return option;
    }
}
