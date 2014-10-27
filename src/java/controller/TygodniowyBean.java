/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import entity.Car;
import entity.Obd2odczyt;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import others.Trasa;
import others.Tygodniowy;

/**
 *
 * @author Adam
 */
public class TygodniowyBean extends Raport{

    /**
     * Creates a new instance of TygodniowyBean
     */
    public TygodniowyBean() {
    }
    private List<Tygodniowy> listaDni = new ArrayList<Tygodniowy>();
    public String generuj(){
        Date j = new Date();
        Date tmp = new Date();
        Car tmpcar = new Car();
        tmp =  null;
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
                for(int ii=1;ii<=7;ii++){
                    tmp=null;
                    Tygodniowy dzien = new Tygodniowy();
                    long tmpdate = this.dataPoczatkowa.getTime()+ (86400000*ii);
                    for (Car z: listaCar){
                        List<Obd2odczyt> list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE o.data>=:datap AND o.data<:datak AND car.vin=:vin").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).setParameter("vin", z.getVin()).getResultList();
                        if (list.isEmpty()){//^^ tuu je błąd w zapytaniu związany z datą początkową, naprawić!
                            continue;
                        }
                        for (Obd2odczyt x: list){
                            if (j != null){
                               long i = abs((j.getTime() - x.getData().getTime())/1000);
                               if (i>12 && tmp != null){
                                   dzien.getTrasy().add(new Trasa(tmpcar,tmp,j));
                                    tmp = null;
                                } 
                            }
                            if (tmp == null)
                               tmp = x.getData();
                            j = x.getData();
                            tmpcar = x.getCar();            
                        }
                }
                if(!dzien.getTrasy().isEmpty())//tu poprawić błąd bo nie dodaje dni w których jest tylko jedna trasa!
                    dzien.getTrasy().add(new Trasa(tmpcar,tmp,j));
                dzien.setData(new Date(tmpdate - 86400000));
                listaDni.add(dzien);                
                dzien = null;
            }
            for(Tygodniowy g: listaDni){
                    System.out.println(g.getData()+ "+ ilość tras: "+ g.getTrasy().size() + "samochodem marki: ");
                    for (Trasa h: g.getTrasy()){
                        System.out.println("trasy: "+h.getCar().getModel()+" daty: "+h.getDataPoczatkowa() + " "+h.getDataKoncowa());
                    }
            }
         em.close();
        }catch(NoResultException e){
            return null;
        }    
    return null;
    }
    
}
