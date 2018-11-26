package shop.controller.security;


import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.dtos.message.Message;
import shop.dtos.security.TokenDTO;
import shop.dtos.security.UsernamePasswordDTO;
import shop.dtos.security.UsernameTokenDTO;
import shop.service.customer.CustomerService;

@RestController
public class AuthenticationRestController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/sign-in")
    public TokenDTO signIn(@RequestBody UsernamePasswordDTO authData)
            throws JwtException {
        return customerService.signIn(authData.getUsername(), authData.getPassword());
    }

}