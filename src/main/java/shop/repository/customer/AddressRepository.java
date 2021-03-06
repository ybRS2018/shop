package shop.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.customer.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
