import java.util.TreeSet; 
import java.io.Serializable;
import java.io.*; 
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.io.IOException;
import static java.lang.System.out;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class Load
{
    private File file;
    private BufferedReader br;
    private UMCarroJa umcj;
     
    public Load(UMCarroJa umcj, String fileName) throws IOException  
    {
        this.umcj = umcj;
        readFile("logs.bak");
    }
    
    /** 
    *  Método que carrega a informação do ficheiro 
    **/
    public void readFile(String fileName) throws IOException 
    {
      try 
      {
           br = new BufferedReader(new FileReader(fileName));
           String line = null;
           while ((line = br.readLine()) != null) 
           {
              registerData(line);  System.out.println("done");
           }
      }catch (IOException e) {e.printStackTrace();} finally {br.close();} 
    }
   
    /** 
    *  Método que faz parse dos dados do ficheiro
    **/
    public void registerData(String c)
    {
       String[] fst = c.split(":");
       String id = fst[0];
       String[] snd = fst[1].split(",");
      // System.out.println(snd[0] + " " + snd[1] + " " + snd[2] + " " +snd[3]);
       switch(id)
       {
           
           case "NovoProp": try{umcj.registerNewOwner(snd[0], Integer.parseInt(snd[1]), snd[2],snd[3]);}
                            catch(RegistrationException e) {out.println(e.getMessage());}
                            break;
                           
           case "NovoCliente": try{umcj.registerNewClient(snd[0], Integer.parseInt(snd[1]), snd[2], snd[3], Double.parseDouble(snd[4]), Double.parseDouble(snd[5]));}
                               catch(RegistrationException e) {out.println(e.getMessage());}
                               break;
                              
           case "NovoCarro": Vehicle v = new Gas();
                             try {v = umcj.vType(snd[0],snd[1],snd[2],Integer.parseInt(snd[3]),Double.parseDouble(snd[4]),Double.parseDouble(snd[5]),Double.parseDouble(snd[6]),Double.parseDouble(snd[7]),Double.parseDouble(snd[8]),Double.parseDouble(snd[9]));}
                             catch (InvalidVehicleException | VehicleExistsException e) {out.println(e.getMessage());}
                             try {umcj.addVehicleToOwner(Integer.parseInt(snd[3]),v);}
                             catch(VehicleExistsException | UserDoesntExistException e) {out.println(e.getMessage());}  
                             break;
                             
           case "Aluguer": Client cl = new Client();
                           Vehicle vh = new Gas();
                           double w = Double.parseDouble(snd[1]); 
                           double z = Double.parseDouble(snd[2]);
                           Point2D pf = new Point2D(w,z); //localização final
                           Point2D p = new Point2D(); //localização do cliente aka inicial
                           try{p = umcj.getLocationbyNif(Integer.parseInt(snd[0]));} catch(UserDoesntExistException e) {out.println(e.getMessage());}    
                           double km = p.distanceTo(pf);
                           LocalDateTime date = LocalDateTime.now(); 
                           double x = 0;
                           double y = 0;
                           try{x = getLocationX(p);} catch(UserDoesntExistException e) {out.println(e.getMessage());} 
                           try{y = getLocationY(p);} catch(UserDoesntExistException e) {out.println(e.getMessage());} 
                           if((snd[3]) == "MaisPerto") 
                           {   
                              try{vh = umcj.nearestVehicle(p,km);} catch(NoVehiclesAvailableException e) {out.println(e.getMessage());}
                              try{cl = umcj.getClientbyNif(Integer.parseInt(snd[0]));} catch(UserDoesntExistException e) {out.println(e.getMessage());}                            
                              
                              double estimatedTime = umcj.estimatedTime(x,y,w,z,vh); 
                              double time = umcj.realTime(estimatedTime, vh);
                              double estimatedPrice = umcj.estimatedPrice(x,y,w,z,vh); 
                              double price = umcj.realPrice(estimatedTime,time, estimatedTime);
                              try
                              {
                                  umcj.endRide(cl,date.getYear(),date.getMonthValue(),date.getDayOfMonth(),date.getHour(),date.getMinute(),
                                           x,y,w,z,vh,km,time,price,estimatedPrice,vh.getAutonomy(),vh.getRating());
                              }catch(DateException e) {out.println(e.getMessage());}
                           }
                           else if((snd[3]) == "MaisBarato")
                                {
                                   try{vh = umcj.nearestVehicle(p,km);} catch(NoVehiclesAvailableException e) {out.println(e.getMessage());}
                                   try{cl = umcj.getClientbyNif(Integer.parseInt(snd[0]));} catch(UserDoesntExistException e) {out.println(e.getMessage());}                            
                           
                                   double estimatedTime = umcj.estimatedTime(x,y,w,z,vh); 
                                   double time = umcj.realTime(estimatedTime, vh);
                                   double estimatedPrice = umcj.estimatedPrice(x,y,w,z,vh); 
                                   double price = umcj.realPrice(estimatedTime,time, estimatedTime);
                                   try
                                   {
                                       umcj.endRide(cl,date.getYear(),date.getMonthValue(),date.getDayOfMonth(),date.getHour(),date.getMinute(),
                                              x,y,w,z,vh,km,time,price,estimatedPrice,vh.getAutonomy(),vh.getRating());
                                   }catch(DateException e) {out.println(e.getMessage());}
                                }
           
                           
           
           case "Classificar": Vehicle vhc = new Gas();
                               if(snd[0].contains ("-")) //se é matricula
                               {
                                  try{ vhc = umcj.getVehiclebyPlate(snd[0]);} catch(VehicleDoesntExistException e) {out.println(e.getMessage());}
                                  vhc.setRating(5);  
                               }
                               else //nif
                               {
                                  try{vhc= umcj.getVehiclebyNif(Integer.parseInt(snd[0]));} catch(VehicleDoesntExistException e) {out.println(e.getMessage());}
                                  vhc.setRating(5); 
                               }
        }      
    }
   
    
   /**
   * Método que devolve a coordenada X de um ponto
   * @param Ponto no espaço 
   * @return Coordenada X
   **/
    public double getLocationX(Point2D p) throws UserDoesntExistException
    {  
       return p.getX();
    }
    
   /**
   * Método que devolve a coordenada Y de um ponto
   * @param Ponto no espaço 
   * @return Coordenada Y
   **/
    public double getLocationY(Point2D p) throws UserDoesntExistException
    { 
       return p.getY();
    }
}  
