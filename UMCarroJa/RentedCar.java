import java.io.Serializable; 

public class RentedCar implements Serializable
{
     private String ownerEmail;
     private String clientEmail;
     private CAR car;
     private double price;
     private Point2D start;
     private Point2D destination;
     private double distance;
     private double autonomy; //pode ser boolean??
     private double walktime;
     private double expectedtime;
     
     public RentedCar(){}
     
     public RentedCar(String ownerEmail, String clientEmail, CAR car, double price, Point2D destination, Point2D start, double distance, 
                    double autonomy, double walktime, double expectedtime)
     {
         this.ownerEmail = ownerEmail;
     }

     public RentedCar(RentedCar r)
     {
         this.ownerEmail = r.getOwnerEmail();
         this.clientEmail = r.getClientEmail();
         this.car = r.getCar();
         this.price = r.getPrice();
         this.start = r.getStart();
         this.destination = r.getDestination();
         this.distance = r.getDistance();
         this.autonomy = r.getAutonomy();
         this.walktime = r.getWalkTime();
         this.expectedtime = r.getExpectedTime();
     }
     
     /**gets**/
     public String getOwnerEmail(){return this.ownerEmail;}
     public String getClientEmail(){return this.clientEmail;}
     public CAR getCar(){return this.car;}
     public double getPrice(){return this.price;}
     public Point2D getStart() {return this.start;}
     public Point2D getDestination() {return this.destination;}
     public double getDistance() {return this.distance;}
     public double getAutonomy() {return this.autonomy;}
     public double getWalkTime() {return this.walktime;}
     public double getExpectedTime() {return this.expectedtime;}
     

     /**sets**/
     public void setOwnerEmail(String ownerEmail) {this.ownerEmail = ownerEmail;}
     public void setClientEmail(String clientEmail) {this.clientEmail = clientEmail;}
     public void setCAR(CAR car) {this.car = car;}
     public void setPrice(double price) {this.price = price;}
     public void setStart(Point2D start) {this.start = start;}
     public void setDestination(Point2D destination) {this.destination = destination;}
     public void setDistance(double distance) {this.distance = distance;}
     public void setAutonomy(double autonomy) {this.autonomy = autonomy;}
     public void setWalkTime(double walktime) {this.walktime = walktime;}
     public void setExpectedTime(double expectedtime) {this.expectedtime = expectedtime;}
     
    
     
     /**CLONE**/
     public RentedCar clone()
     {
         return new RentedCar(this); //IMPLEMENTAR DIFERENTE!!!!! foi só para funcionar no historico do cliente
     }
     
     
     /**ToString**/
     
     
     
     /**há mais um que não me lembro**/
     
     
     
     /** o cliente indica as coordenadas x e y em que se encontra e para
     * onde se pretende deslocar;
     */ 
       
       
     /** o cliente decide qual carro pretende alugar (de acordo com as 
     * possibilidades oferecida pela aplicação e descritas anteriormente);
     */
     
     
     /** De seguida é indicado ao cliente se o carro pretendido tem 
     autonomia para efectuar a viagem, e caso o tenha, é indicado o custo 
     estimado da viagem, tendo em conta o deslocamento que é necessário 
     efectuar; */
        
        
     /** Caso o veículo tenha autonomia, o proprietário é notficado do 
     *pedido de aluguer e do tempo que o cliente demora a chegar a pé 
     *ao veiculo. O proprietário poderá aceitar ou não o pedido de 
     *aluguer.*/
    
        
     /**de acordo com a 􏰀abilidade do carro (e de outros factores que pode considerar: 
     * a destreza do condutor, as condições meteorológicas, etc.) é calculado o tempo 
     *real da viagem.*/
     
     /**o carro fica no ponto definido como fim da viagem à espera de novo aluguer*/
     //No final da viagem fazer update da localização do carro = destino dado pelo cliente
     
     /** após a viagem o cliente e o proprietário podem dar uma avaliação ao carro ao cliente,
      *  e 􏰀cam com o registo relativo à viagem guardado nas suas áreas pessoais.
      */
}
