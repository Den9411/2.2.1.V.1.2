package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar (String model, int series) {
      String hql1 = "FROM Car C WHERE C.model = :model and  C.series = :series";
      Query query1 = sessionFactory.getCurrentSession().createQuery(hql1);
      query1.setParameter("model", model);
      query1.setParameter("series", series);
      List<Car> cars = query1.list();

      Car car = cars.get(0);
      String hql2 = "FROM User U WHERE U.car = " + car.getId();
      Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
      List<User> users = query2.list();
      return users.get(0);
   }
}
