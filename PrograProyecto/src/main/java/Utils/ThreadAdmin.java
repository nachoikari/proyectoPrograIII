
package Utils;

import Model.Admin;
import org.json.JSONArray;
import org.json.JSONObject;

public class ThreadAdmin extends Thread {
    private String url="http://127.0.0.1:5000";
    public String method="";
    public String acction = "";
    //===============
    public String ced="";
    public String token ="";
    public String password="";
    public String name="";
    public String email = "";
    private JSONArray adminsArray;
    private Admin admin;
    
    public ThreadAdmin(){
        
    }
    @Override
    public void run(){
        menu();
    }
    private void menu(){
        if ("POST".equals(method)){
           
            postOptions();
        }else if("PUT".equals(method)){
            
        }else if("DELETE".equals(method)){
            delete();
        }else if("GET".equals(method)){
            
        }else{
            System.out.println("Method not valid");
        }
    }
    void postOptions(){
        if("REGISTER".equals(acction)){
            register();
        }else if("CREATE".equals(acction)){
            create();
        }else if("LOGIN".equals(acction)){
            login();
        }
    }
    private void create(){
        String data = "ced=" + ced + "&password=" + password + "&email=" + email + "&name=" + name + "&token=" + token;
        String urlConsult = url+"/admin/create";
        String response = RemoteConnection.connectToServer(urlConsult, method, data);
        if(response !=null && !response.isEmpty()){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if(code == 1){
                String ced = jsonResponse.optString("admin");
                System.out.println("admin");
            }
        }  
    }
    
    private void register(){
        String data = "ced="+ced+"&password="+password+"&email="+email+"&name="+name;
        String urlConsult = url+"/admin/register";
        String response = RemoteConnection.connectToServer(urlConsult, method, data);
        if(response !=null && !response.isEmpty()){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if(code == 1){
                String ced = jsonResponse.optString("ced");
                System.out.println("ced");
            }
        }
        
    }
    
    private void login(){
        //data es el cuerpo de la consulta
        String data = "ced="+ced+"&password="+password;
        String urlConsult = url+"/admin/login";
        String response = RemoteConnection.connectToServer(urlConsult, "POST", data);
        if ( response != null && !response.isEmpty() ){
            JSONObject jsonResponse = new JSONObject(response);
            
            int code = jsonResponse.optInt("code");
            if (code == 1){
                String token = jsonResponse.optString("token");
                admin.setToken(token);
                admin.setIsLogged(true);
                System.out.println(admin.getToken());
            }
        }  
    }
    private void delete(){
        String data = "ced="+ced+"&token="+token;
        String urlConsult = url+"/admin/delete";
        String response = RemoteConnection.connectToServer(urlConsult, method, data);
        if(response != null && !response.isEmpty()){
            JSONObject jsonResponse = new JSONObject(response);
            
            int code = jsonResponse.optInt("code");
            if(code == 1){
                System.out.println("Admin deleted");
            }
        }
    }
    
}
