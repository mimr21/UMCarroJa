import java.util.*;
import java.io.Serializable;
/**
 * DESCREVER ESTA CLASSE DE UMA MANEIRA PANELEIRA 
 */
public class Owner extends USER implements Serializable
{
  private int rating;
  //HISTORICA VAI SER UMA ESTRUTURA QQ


  /**
  * Construtor por omissão - sem parametros/vazio
  **/
  public Owner()
  {
   }

  public Owner(String email,String name,String password,String address,String date,int rating)

  {
     super(email, name,password,address,date);
     this.rating= 0;
  }
     

  /**
  * Construtor parametrizado - define um valor incial para 
  * cada variavel
  **/
   
  public Owner(int new_rating)
  {
     this.rating = new_rating;
  }
    

  /**
  * Construtor de cópia - recebe um objeto e cria uma cópia dele
  **/
  public Owner(Owner outro)
  {}

  public Owner(Owner o)
  {
     this.rating = o.getRating();
  }
    
    /************************* GETTERS *************************/
  public int getRating(){return this.rating;}
  
   /************************* SETTERS *************************/
  public void setRating(int newRating){this.rating = newRating;}

   /************************* CLONE *************************/
  public Owner clone()
  {
    return new Owner(this); //IMPLEMENTAR DIFERENTE!!!
  }
    
    /************************* EQUALS *************************/
  public boolean equals(Object o)
  {
      if(this == o) return true;
      if(o != null && this.getClass() != o.getClass()) return false;
      Owner p = (Owner) o;     
       return this.rating == p.getRating();
               
  }
    
    /************************* TOSTRING *************************/
  public String toString()
  {
      return "Classificação: " + rating;
             
  }
    
}


