import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Stream;
import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.*; 
import java.util.stream.Collectors;

public class UMCarroJa implements Serializable
{
   private Map<Integer, Client> clients;            //NIF
   private Map<Integer, Owner> owners;              //NIF
   private Map<Integer, Vehicle> vehicles;          //NIF
   
   /** Construtor vazio que cria uma instância UMCarroJa **/
   public UMCarroJa()
   {
       this.clients = new HashMap<Integer,Client>();
       this.owners = new HashMap<Integer,Owner>();
       this.vehicles = new HashMap<Integer,Vehicle>();
   }
   
   /** Construtor que cria uma nova instância UMCarroJa a partir de um UMCarroJa passado como parâmetro **/
   public UMCarroJa(UMCarroJa u)
   {
       this.clients = u.getClients();
       this.owners = u.getOwners();
       this.vehicles = u.getVehicles();
   }
   
   /** Método que devolve os veiculos inscritos na aplicação **/
   public Map<Integer,Vehicle> getVehicles()
   {
       Map<Integer,Vehicle> vh = new HashMap<Integer,Vehicle>();
       for(Map.Entry<Integer, Vehicle> e : this.vehicles.entrySet()) {vh.put(e.getKey(), e.getValue().clone());}
       return vh;
   }
   
   /** Método que define um hashMap a partir de um hashMap passado como parâmetro **/
   public void setVehicles(Map<Integer,Vehicle> vh)
   {
       this.vehicles.clear();
       for(Map.Entry<Integer,Vehicle> e : vh.entrySet()) {this.vehicles.put(e.getKey(), e.getValue().clone());}
   }
   
   //Método que devolve os clientes inscritos na aplicação
   public Map<Integer,Client> getClients()
   {
       Map<Integer,Client> cl = new HashMap<Integer,Client>();
       for(Map.Entry<Integer, Client> c : this.clients.entrySet()) {cl.put(c.getKey(), c.getValue().clone());}
       return cl;
   }
   
   //Método que define um hashMap a partir de um hashMap passado como parâmetro
   public void setClients(Map<Integer,Client> cl)
   {
       this.clients.clear();
       for(Map.Entry<Integer,Client> c : cl.entrySet()) {this.clients.put(c.getKey(), c.getValue().clone());}
   }
   
   //Método que devolve os clientes inscritos na aplicação
   public Map<Integer,Owner> getOwners()
   {
       Map<Integer,Owner> ow = new HashMap<Integer,Owner>();
       for(Map.Entry<Integer, Owner> o : this.owners.entrySet()) {ow.put(o.getKey(), o.getValue().clone());}
       return ow;
   }
   
   //Método que define um hashMap a partir de um hashMap passado como parâmetro
   public void setOwners(Map<Integer,Owner> ow)
   {
       this.owners.clear();
       for(Map.Entry<Integer,Owner> o : ow.entrySet()) {this.owners.put(o.getKey(), o.getValue().clone());}
   }
   
   //Devolve uma representação, no formato textual, de uma instância UMCarroJa 
   public String toString(){return this.vehicles.toString()+"\n"+this.owners.toString()+"\n"+this.clients.toString()+"\n";}
   
   //Método que cria uma cópia de uma instância UMCarroJa
   public UMCarroJa clone(){return new UMCarroJa(this); }
   
   //Método que testa se um objeto é igual a uma determinada UMCarroJa
   public boolean equals(Object o)
   {
       if(this == o) return true;
       if((o == null) || (this.getClass() != o.getClass())) return false; 
       UMCarroJa u = (UMCarroJa) o;
       return  this.vehicles.equals(u.getVehicles()) && this.clients.equals(u.getClients()) && this.owners.equals(u.getOwners());
   }
  
   
   //Método que valida o acesso de um utilizador na aplicação através do seu email e password
   public USER validateAcess(int nif, String email) throws LoginException
   {
       boolean cl = this.clients.values().stream().anyMatch(u -> u.getEmail().equals(email) && u.getNif() == nif);
       boolean ow = this.owners.values().stream().anyMatch(u -> u.getEmail().equals(email) && u.getNif() == nif);
       if (!cl && !ow) throw new LoginException("Acesso inválido.");   
       if(cl){return this.clients.get(nif).clone();}
       else{return this.owners.get(nif).clone();}   
   }
   
      
   
   /*** CLIENTES ***/
   //Método que regista um novo cliente na aplicação
   public Client registerNewClient(String name, int nif, String email, String  address, double x, double y) throws RegistrationException 
   {
       Client c = new Client(name, nif, email, address, x, y);
       
       try{addCL(c);} 
       catch(UserExistsException e){throw new RegistrationException("Registo Inválido");}
       
       return c.clone();
   }  
   //Método que adiciona um cliente à aplicação
   public void addCL(Client c) throws UserExistsException 
   {
       if(!this.clients.containsKey(c.getEmail())) this.clients.put(c.getNif(), c.clone());
       else throw new UserExistsException("Cliente já existe.");    
   } 
   //Método que remove um cliente da aplicação
   public void deleteCL(Client c){this.clients.remove(c.getNif());}
   
   //Método que retorna uma lista com os 10 clientes que mais utilizam o sistema (em vezes)
   public List<Client> top10clientsX()
   {
       Set<Client> clOrder = new TreeSet<Client>(new ClientOrderX());
       List<Client> clList = new ArrayList<Client>(); 
       
       for(Client c: this.clients.values()){clOrder.add(c.clone());}    
       Iterator<Client> co = clOrder.iterator();
       int i = 0;
       while(co.hasNext() && i<10)
       {
           clList.add(co.next());
           i++;
       }
       return clList;
   }
   
   
   //Método que retorna uma lista com os 10 clientes que mais utilizam o sistema (em km)
   public List<Client> top10clientsKM()
   {
      Set<Client> clOrder = new TreeSet<Client>(new ClientOrderKM());  
      List<Client> clList = new ArrayList<Client>(); 
       
       for(Client c: this.clients.values()){clOrder.add(c.clone());}    
       Iterator<Client> co = clOrder.iterator();
       int i = 0;
       while(co.hasNext() && i<10)
       {
           clList.add(co.next());
           i++;
       }
       return clList;
   }
   
   
   

      
    
   /*** Proprietários ***/
   //Método que devolve um owner
   public Owner getOwnerByNif(int nif) throws UserDoesntExistException
   {
       if(!this.owners.containsKey(nif))
              throw new  UserDoesntExistException("O nif inserido não existe na nossa base de dados, por favor retifique a informação");
       else
            return this.owners.get(nif);
   }
    
   //Método que regista um novo proprietário na aplicação
   public Owner registerNewOwner(String name, String email, String  address, int nif) throws RegistrationException, UserExistsException
   {
       Owner o = new Owner(name, nif,  email, address);
       addOW(o);
       return o.clone();
   }  
   
   //Método que adiciona um proprietario à aplicação
   public void addOW(Owner o) throws UserExistsException, RegistrationException 
   {
       if(!this.owners.containsKey(o.getNif())) this.owners.put(o.getNif(), o.clone());
       else throw new UserExistsException("Proprietário já existe.");    
   }
   
   //Método que remove um proprietário da aplicação
   public void deleteOW(Owner o){this.owners.remove(o.getNif());}
   
   //Método que retorna uma lista com todos os veículos de um determinado proprietario
   public List<Vehicle> listOfVehicles(int nif) throws UserDoesntExistException
   { 
       if(!this.owners.containsKey(nif)) 
            throw new  UserDoesntExistException("O email inserido não existe na nossa base de dados, por favor retifique a informação");
       else
       {
           List<Vehicle> v = new ArrayList<Vehicle>();
           Owner o = this.owners.get(nif);
           for(Vehicle vh: o.getVehicles().values()){v.add(vh); }
           return v;
       }
   }
   
   /**
  * Método que permite o proprietário aceitar ou recusar um aluguer 
  * @param Cliente que requisitou o aluguer
  * @param Veículo a alugar
  **/
  public boolean acceptORreject(Client c, Vehicle v) throws UserDoesntExistException
  {
     int nif = v.getNif();
     Owner o = new Owner();
     if(!this.owners.containsKey(nif))
            throw new UserDoesntExistException("O nif inserido não existe na nossa base de dados, por favor retifique a informação");
     else
        return true;
  }
   
   
   
   
   
   /*** Vehicles ***/
   public void  addVehicleToOwner(int nif, Vehicle v) throws VehicleExistsException, UserDoesntExistException
   {
       if(!this.owners.containsKey(nif)) throw new  UserDoesntExistException("Este proprietário não existe na nossa base de dados");
       if(vehicleExists(v.getNif())) 
            throw new VehicleExistsException("Matrícula já existente.");
       else
       {
           Owner o = this.owners.get(nif);
           v.setNif(o.getNif());
           o.addVehicle(v); 
       }
   }
   
   //Método que verifica se um veículo já existe a partir da sua matrícula
   public boolean vehicleExists(int nif)
   {
     boolean p = this.vehicles.containsKey(nif);
     return p;
   }

  
   //Método que verifica o tipo de veículo que se pretende criar e que cria o veículo em questão a partir de funções auxiliares
   public Vehicle vType(String type, String brand, String plate, int nif, double speed, double price, double comsuption, double autonomy, double x, double y) throws InvalidVehicleException,  VehicleExistsException
   {
       Vehicle v;
       if(type.equals("Gasolina")) v = gasRegistration(type,brand, plate, nif, speed, price, comsuption, autonomy, x, y);
       else if(type.equals("Eletrico")) v = electricRegistration(type,brand, plate, nif, speed, price, comsuption, autonomy, x, y);
            else if(type.equals("Monovolume")) v = hybridRegistration(type,brand, plate, nif, speed, price, comsuption, autonomy, x, y);
                 else{throw new InvalidVehicleException("Classe de veículo inválida, tente novamente.");}
       return v;
   }
   
   //Método que cria um novo carro a gasolina
   public Vehicle gasRegistration(String type, String brand, String plate, int nif, double speed, double price, double comsuption, double autonomy, double x, double y) throws VehicleExistsException
   {         
       if(vehicleExists(nif)) 
            throw new VehicleExistsException("Matrícula já existe.");
       else
       {
           Point2D p = new Point2D(x,y);
           Gas g = new Gas(type,brand,plate,nif,speed,price,comsuption,autonomy,x, y);
           return g.clone();
       }
   }
   
   //Método que cria um novo carro eletrico
   public Vehicle electricRegistration(String type, String brand, String plate, int nif, double speed, double price, double comsuption, double autonomy, double x, double y) throws VehicleExistsException
   {         
       if(vehicleExists(nif)) 
            throw new VehicleExistsException("Matrícula já existe.");
       else
       {
          Point2D p = new Point2D(x,y);
          Electric e = new Electric(type,brand,plate,nif,speed,price,comsuption,autonomy,x, y);
          return e.clone();
       }
   }
   
   //Método que cria um novo carro hibrido
   public Vehicle hybridRegistration(String type, String brand, String plate, int nif, double speed, double price, double comsuption, double autonomy, double x, double y) throws VehicleExistsException
   {         
       if(vehicleExists(nif)) 
            throw new VehicleExistsException("Matrícula já existente.");
       else
       {
           Point2D p = new Point2D(x,y);
           Hybrid h = new Hybrid(type,brand,plate,nif,speed,price,comsuption,autonomy,x, y);
           return h.clone();
       }
   }
   
   //Método que retorna o veículo mais próximo da localização de um determinado cliente
   public Vehicle nearestVehicle(double x, double y) throws NoVehiclesAvailableException
   {
       Point2D clLocation = new Point2D(x,y);
       Point2D vlLocation;
       double dist = -1;
       Vehicle vf = new Gas();
       
       for(Vehicle vh : this.vehicles.values())
       {
              if(vh.getAvailability())
              {
                   vf = vh;
                   vlLocation = vh.getLocation();
                   if(dist == -1)
                   {
                       vh = vf;
                       dist = vlLocation.distanceTo(clLocation);
                   }
                   else if(vlLocation.distanceTo(clLocation) < dist)
                   {
                       vh = vf;
                       dist = vlLocation.distanceTo(clLocation);
                   }
              }
       }
       return vf;
   }
   
   //Método que retorna o veículo de uma  requisitado por um determinado cliente
   public Vehicle specificVehicle(String plate) throws VehicleDoesntExistException 
   {
      Vehicle v;
      if(!this.vehicles.containsKey(plate)) throw new VehicleDoesntExistException("O veículo inserido não existe na nossa base de dados");
      else
      {
        Vehicle v1;
        v1 = this.vehicles.get(plate);
        v = v1.clone();
      }
      return v;
   }
   
   //Método que retorna o carro mais barato
   public Vehicle cheapestVehicle() throws NoVehiclesAvailableException
   {
      Set<Vehicle> vOrder = new TreeSet<Vehicle>(new VehicleOrderP()); 
      Vehicle v;
      if(vOrder.size() == 0) throw new NoVehiclesAvailableException("Não existem veículos disponiveis");
      else
      {
          for(Vehicle c: this.vehicles.values()){vOrder.add(c.clone());}    
          Iterator<Vehicle> i = vOrder.iterator();
          v = i.next();
      }
      return v;
   } 
  
   //Método que retorna o carro com a walk mais barata
   public Vehicle cheapestWalkVehicle(double walk, Point2D localc) throws NoVehiclesAvailableException
   {
       Set<Vehicle> near;
       near = this.vehicles.values().stream().filter(u -> u.isNear(walk, u.getLocation(), localc)).collect(Collectors.toCollection(TreeSet::new));
       Set<Vehicle> vOrder = new TreeSet<Vehicle>(new VehicleOrderP()); 
       Vehicle v;
       if(vOrder.size() == 0) throw new NoVehiclesAvailableException("Não existem veículos disponiveis");
       else
       {
          for(Vehicle c: near){vOrder.add(c.clone());}    
          Iterator<Vehicle> i = vOrder.iterator();
          v = i.next();
       }
       return v;
   }
   
   
   public Vehicle desiredAutonomyVehicle(double autonomy) throws NoVehiclesAvailableException
   {
       Set<Vehicle> near;
       near = this.vehicles.values().stream().filter(u -> u.hasAutonomy(autonomy)).collect(Collectors.toCollection(TreeSet::new));
       Set<Vehicle> vOrder = new TreeSet<Vehicle>(new VehicleOrderA()); 
       Vehicle v;
       if(vOrder.size() == 0) throw new NoVehiclesAvailableException("Não existem veículos disponiveis");
       else
       {
          for(Vehicle c: near){vOrder.add(c.clone());}    
          Iterator<Vehicle> i = vOrder.iterator();
          v = i.next();
       }
       return v;
   }
   
   
  
   //Método que determina o tempo estimado de uma viagem
   public double estimatedTime(double x, double y, double w, double z, Vehicle v)
   {
       Point2D client = new Point2D(x,y);
       Point2D d = new Point2D(w,z);
       double dist1 = v.getLocation().distanceTo(client); 
       double dist2 = client.distanceTo(d);
       return Math.round((dist1 + dist2) / v.getSpeed());
   } 
   //Método que determina o tempo real de uma viagem
   public double realTime(double estimatedTime, Vehicle v)
   {                                       
       double calc = chanceofrain() * 0.003 + chanceoffog() * 0.001 + chanceoftraffic() * 0.0015;
       return Math.round(estimatedTime / calc);
   }
   //Método que determina o custo estimado de uma viagem
   public double estimatedPrice(double x, double y, double w, double z, Vehicle v)
   {
       Point2D client = new Point2D(x,y);
       Point2D d = new Point2D(w,z);
       double distance = client.distanceTo(d);
       double estimatedTime = estimatedTime(x, y, w, z, v);
       return Math.round(distance * v.getPrice() + estimatedTime * 0.10 );
   }
   //Método que determina o custo real de uma viagem
   public double realPrice(double estimatedTime, double realTime, double estimatedPrice)
   {
       double realPrice;
       if((realTime / estimatedTime) > 1.25)  realPrice = estimatedPrice;
       else realPrice = Math.round( estimatedPrice + (realTime - estimatedTime) * 0.25);
       return realPrice;
   }
   
   public double chanceofrain(){return Math.round(Math.random() * 100);}
   public double chanceoffog(){return Math.round(Math.random() * 100);}
   public double chanceoftraffic(){return Math.round(Math.random() * 100);}
   
   
   
   //Método que retorna o total faturado por um veiculo num determinado período
   public double carProfit(String plate, int yi, int mi, int di, int yf, int mf, int df) throws DateException, VehicleDoesntExistException
   {
      double total = 0;
       if(yi < 0 || mi < 1 || mi > 12 || di < 1 || di > 31 || yf < 0 || mf < 1 || mf > 12 || df < 1 || df > 31 || yi > yf || 
         (yi == yf && mi > mf) || (yi == yf && mi == mf && di > df))
          throw new DateException("Formato de data e hora incorreto, por favor tente novamente com uma data e hora válidas.");
      else if(!this.vehicles.containsKey(plate)) 
                throw new VehicleDoesntExistException("A matricula inserida não existe na nossa base de dados, por favor retifique a informação");
           else{ 
                  LocalDateTime i = LocalDateTime.of(yi,mi,di,00,00);
                  LocalDateTime f = LocalDateTime.of(yf,mf,df,23,59);
            
                  Vehicle v, v1;
                 
                  v1 = this.vehicles.get(plate);
                  v = v1.clone();
                  
                  Ride r;
                  Iterator<Ride> it = v.getRentingHistoryAll().iterator();
                  while(it.hasNext())
                  {
                      r = it.next();  
                      if((r.getDate().isAfter(i) || r.getDate().equals(i)) && (r.getDate().isBefore(f) || r.getDate().equals(f)))
                            total += r.getRealPrice();
                  }
                  
                  return total;
                }
   }
   
   /**
   public double ownerProfit(String email, int yi, int mi, int di, int yf, int mf, int df) throws DateException, UserDoesntExistException
   {
      double total = 0;
      if(yi < 0 || mi < 1 || mi > 12 || di < 1 || di > 31 || yf < 0 || mf < 1 || mf > 12 || df < 1 || df > 31 || yi > yf || 
         (yi == yf && mi > mf) || (yi == yf && mi == mf && di > df))
          throw new DateException("Formato de data e hora incorreto, por favor tente novamente com uma data e hora válidas.");
          else if(!this.owners.containsKey(email)) throw new UserDoesntExistException("O utilizador inserido não existe na nossa base de dados, por favor retifique a informação");
          else{ 
                  LocalDateTime i = LocalDateTime.of(yi,mi,di,00,00);
                  LocalDateTime f = LocalDateTime.of(yf,mf,df,23,59);
                  
                  Owner o = this.owners.get(email);
       
                  for(RentedCar r: o.getRentingHistory())
                  {
                      if((r.getDate().isAfter(i) || r.getDate().equals(i)) && (r.getDate().isBefore(f) || r.getDate().equals(f)))
                            total += r.getRealPrice();
                  }
                  
             }
     return total;
   } */
   
   
   
   /*** VIAGEM/ALUGUER ***/
   //Método que retorna o registo de viagens de um utilizador num determinado período
   public List<RentedCar> rentingRegist(String email, int yi, int mi, int di, int yf, int mf, int df) throws DateException
   {
      if(yi < 0 || mi < 1 || mi > 12 || di < 1 || di > 31 || yf < 0 || mf < 1 || mf > 12 || df < 1 || df > 31 || yi > yf || 
         (yi == yf && mi > mf) || (yi == yf && mi == mf && di > df))
          throw new DateException("Formato de data e hora incorreto, por favor tente novamente com uma data e hora válidas.");
      else{  
          LocalDateTime i = LocalDateTime.of(yi,mi,di,00,00);
          LocalDateTime f = LocalDateTime.of(yf,mf,df,23,59);
          List<RentedCar> r = new ArrayList<RentedCar>();
          
          if(this.clients.containsKey(email))
          {
               Client c = this.clients.get(email);
               for(RentedCar rc: c.getRentingHistoryAll())
               {
                  if((rc.getDate().isAfter(i) || rc.getDate().equals(i)) && (rc.getDate().isBefore(f) || rc.getDate().equals(f)))
                       r.add(rc);
               }
          }
          else if (this.owners.containsKey(email))
          {
              Owner o = this.owners.get(email);
              for(RentedCar rc: o.getRentingHistoryAll()) 
              {
                  if((rc.getDate().isAfter(i) || rc.getDate().equals(i)) && (rc.getDate().isBefore(f) || rc.getDate().equals(f)))
                       r.add(rc);
              }   
          }
          return r;
      }
   }
   
   //Método que regista a viagem efetuada no histórico do veiculo e do cliente 
   public void endRide(Client c, int yr, int m, int d, int h, int min, double x, double y, double w, double z, Vehicle v, double kms, 
                                double timeRide, double custoReal, double custoEst, double a, int classificacao) throws DateException{
       if(y < 0 || m < 1 || m > 12 || d < 1 || d > 31 || h < 0 || h > 23 || min < 0 || min > 59)
           throw new DateException("Formato de data e hora incorreto, por favor tente novamente com uma data e hora válidas.");
       else{
           LocalDateTime date = LocalDateTime.of(yr,m,d, h, min);
           
           Point2D i = new Point2D(x,y);
           Point2D f = new Point2D(w,z);
           
           int onif = v.getNif();
           Owner o = this.owners.get(onif);
           
           //Historicos atualizados
           RentedCar rc = new RentedCar(o.getEmail(), c.getEmail(), v, custoReal, f, i, kms, a, date, timeRide);
           c.addRentedCar(rc);
           o.addRentedCar(rc);
           
           Ride r = new Ride(c.getEmail(), date, i, f, kms, timeRide, custoReal);
           v.addRide(r);
           
           //Caracteristicas do Carro
           if(!v.hasAutonomy())
                o.refuel(v.getPlate());
                
               v.setAvailability(true);
               v.setX(w);
               v.setY(z);
               v.setLocation(f);
               v.setRating(classificacao);
               
           //Classificação do owner
           o.ownerRating();
           
           //Atualiza a posição do cliente
           c.setX(w);
           c.setY(z);
           c.setLocation(f);
           
       }
   }
   
  

   
  
   
   
   /*** STATUS ***/
   
   //Método que guarda o estado de uma instância num ficheiro de texto.
   public void writeToTxt(String fileName) throws IOException 
   {
       PrintWriter fich = new PrintWriter(fileName);
       fich.println("------- UMCJ --------");
       fich.println(this.toString());
       fich.flush();
       fich.close();
   }
   //Método que guarda em ficheiro de objectos o objecto que recebe a mensagem.
   public void saveStatus(String fileName) throws FileNotFoundException,IOException 
   {
       FileOutputStream fos = new FileOutputStream(fileName);
       ObjectOutputStream oos = new ObjectOutputStream(fos);
       oos.writeObject(this); //guarda-se todo o objecto de uma só vez
       oos.flush();
       oos.close();
   }
    // Método que recupera uma instância de UMCarroJa de um ficheiro de objectos.
   public static UMCarroJa loadStatus(String fileName) throws FileNotFoundException,IOException, ClassNotFoundException 
   {
      FileInputStream fis = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fis);
      UMCarroJa umcj = (UMCarroJa) ois.readObject();
      ois.close();
      return umcj;
   }
   //Corre a aplicação, gerando um menu interativo
   public static void main()
   {
       new App().run();
   }
}
