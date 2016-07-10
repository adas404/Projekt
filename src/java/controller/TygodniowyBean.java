/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBManager;
import entity.Car;
import entity.Obd2odczyt;
import entity.Uzytkownik;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
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

    public List<Tygodniowy> getListaDni() {
        return listaDni;
    }

    public void setListaDni(List<Tygodniowy> listaDni) {
        this.listaDni = listaDni;
    }
    private List<Tygodniowy> listaDni = new ArrayList<Tygodniowy>();
    public void przygotuj(){
        this.dataKoncowa = null;
        this.dataPoczatkowa = null;
        listaDni = new ArrayList<Tygodniowy>();
    }
    public String generuj(){
        Date j = new Date();
        Date tmp = new Date();
        Car tmpcar = new Car();
        tmp =  null;
        List<Obd2odczyt> list = new ArrayList<Obd2odczyt>();
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        int typ = (Integer)session.getAttribute("typ");
        int id =  (Integer)session.getAttribute("id");
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
                for(int ii=1;ii<=7;ii++){
                    tmp=null;
                    Tygodniowy dzien = new Tygodniowy();
                    long tmpdate = this.dataPoczatkowa.getTime()+ (86400000*ii);
                    for (Car z: listaCar){
                        list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE o.data>=:datap AND o.data<:datak AND car.vin=:vin").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).setParameter("vin", z.getVin()).getResultList();
                        if (list.isEmpty()){
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
                    list = em.createQuery("SELECT o FROM Obd2odczyt o JOIN o.car car WHERE o.data>=:datap AND o.data<:datak AND car.vin=:vin").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).setParameter("vin", "1K80441").getResultList();
                    if(!list.isEmpty()){
                        if (typ==1){
                            dzien.setMaksymalnaPredkosc((Integer)em.createQuery("SELECT MAX(o.predkosc) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setSredniaPredkosc((Double)em.createQuery("SELECT AVG(o.predkosc) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMinimalnaPredkosc((Integer)em.createQuery("SELECT MIN(o.predkosc) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMaksymalnaTempChlodzacego((Integer)em.createQuery("SELECT MAX(o.tempChlodzacego) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMinimalnaTempChlodzacego((Integer)em.createQuery("SELECT MIN(o.tempChlodzacego) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setSredniaTempChlodzacego((Double)em.createQuery("SELECT AVG(o.tempChlodzacego) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMaksymalnaTempDolotu((Integer)em.createQuery("SELECT MAX(o.tempDolotu) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMinimalnaTempDolotu((Integer)em.createQuery("SELECT MIN(o.tempDolotu) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setSredniatemDolotu((Double)em.createQuery("SELECT AVG(o.tempDolotu) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMaksymalneCisnienieKolektora((Integer)em.createQuery("SELECT MAX(o.cisnienieKolektora) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMinimalneCisnienieKolektora((Integer)em.createQuery("SELECT MIN(o.cisnienieKolektora) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setSrednieCisnienieKolektora((Double)em.createQuery("SELECT AVG(o.cisnienieKolektora) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMaksymalneObciazenieSilnika((Integer)em.createQuery("SELECT MAX(o.obciazenieSilnika) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMinimalneObciazenieSilnika((Integer)em.createQuery("SELECT MIN(o.obciazenieSilnika) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setSrednieObciazenieSilnika((Double)em.createQuery("SELECT AVG(o.obciazenieSilnika) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMaksymalneObroty((Double)em.createQuery("SELECT MAX(o.obroty) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMinimalneObroty((Double)em.createQuery("SELECT MIN(o.obroty) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setSrednieObroty((Double)em.createQuery("SELECT AVG(o.obroty) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMaksymalnePolozeniePrzepustnicy((Integer)em.createQuery("SELECT MAX(o.polozeniePrzepustnicy) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setMinimalnePolozeniePrzepustnicy((Integer)em.createQuery("SELECT MIN(o.polozeniePrzepustnicy) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());
                            dzien.setSredniePolozeniePrzepustnicy((Double)em.createQuery("SELECT AVG(o.polozeniePrzepustnicy) FROM Obd2odczyt o JOIN o.car car WHERE car.vin=ANY(SELECT car.vin from Car car JOIN car.uzytkownik u where u.id=:id) AND o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak",  new Date(tmpdate)).setParameter("id", id).getSingleResult());

                        }else if(typ==2){
                            dzien.setMaksymalnaPredkosc((Integer)em.createQuery("SELECT MAX(o.predkosc) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setSredniaPredkosc((Double) em.createQuery("SELECT AVG(o.predkosc) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMinimalnaPredkosc((Integer) em.createQuery("SELECT MIN(o.predkosc) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMaksymalnaTempChlodzacego((Integer) em.createQuery("SELECT MAX(o.tempChlodzacego) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMinimalnaTempChlodzacego((Integer) em.createQuery("SELECT MIN(o.tempChlodzacego) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setSredniaTempChlodzacego((Double) em.createQuery("SELECT AVG(o.tempChlodzacego) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMaksymalnaTempDolotu((Integer) em.createQuery("SELECT MAX(o.tempDolotu) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMinimalnaTempDolotu((Integer) em.createQuery("SELECT MIN(o.tempDolotu) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setSredniatemDolotu((Double) em.createQuery("SELECT AVG(o.tempDolotu) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMaksymalneCisnienieKolektora((Integer) em.createQuery("SELECT MAX(o.cisnienieKolektora) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMinimalneCisnienieKolektora((Integer) em.createQuery("SELECT MIN(o.cisnienieKolektora) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setSrednieCisnienieKolektora((Double) em.createQuery("SELECT AVG(o.cisnienieKolektora) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMaksymalneObciazenieSilnika((Integer) em.createQuery("SELECT MAX(o.obciazenieSilnika) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMinimalneObciazenieSilnika((Integer) em.createQuery("SELECT MIN(o.obciazenieSilnika) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setSrednieObciazenieSilnika((Double) em.createQuery("SELECT AVG(o.obciazenieSilnika) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMaksymalneObroty((Double) em.createQuery("SELECT MAX(o.obroty) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMinimalneObroty((Double) em.createQuery("SELECT MIN(o.obroty) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setSrednieObroty((Double) em.createQuery("SELECT AVG(o.obroty) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMaksymalnePolozeniePrzepustnicy((Integer) em.createQuery("SELECT MAX(o.polozeniePrzepustnicy) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setMinimalnePolozeniePrzepustnicy((Integer) em.createQuery("SELECT MIN(o.polozeniePrzepustnicy) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());
                            dzien.setSredniePolozeniePrzepustnicy((Double) em.createQuery("SELECT AVG(o.polozeniePrzepustnicy) FROM Obd2odczyt o WHERE o.data>=:datap AND o.data<=:datak").setParameter("datap", new Date(tmpdate-86400000)).setParameter("datak", new Date(tmpdate)).getSingleResult());

                        }
                    }
                if(!dzien.getTrasy().isEmpty())
                    dzien.getTrasy().add(new Trasa(tmpcar,tmp,j));
                dzien.setData(new Date(tmpdate - 86400000));
                dzien.setIloscTras(dzien.getTrasy().size());
                listaDni.add(dzien);                
                list.clear();
            }
            for(Tygodniowy g: listaDni){
                    long tmptime = 0;
                    for (Trasa h: g.getTrasy()){
                        tmptime += h.getDataKoncowa().getTime()-h.getDataPoczatkowa().getTime();                     
                    }
                    g.setCzasJazdy(new Date(tmptime));
                    g.setIloscKilometrow((g.getSredniaPredkosc()) * ((double)(new Date(tmptime).getHours()-1) + (((double)(new Date(tmptime).getMinutes()))/60) + (((double)(new Date(tmptime).getSeconds()))/3600)));
            }
            this.log(4, "Wygenerowano raport tygodniowy" , em.find(Uzytkownik.class, session.getAttribute("id")));
         em.close();
         
        }catch(NoResultException e){
            return null;
        }    
    return "tygodniowy";
    }
    
}
