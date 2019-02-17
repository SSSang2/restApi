package me.json.demorestapi.accounts;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByUsername(){
        //Given
        String username = "woosangc.lee@samsung.com";
        String password = "woosang";

        Account account = Account.builder()
                .email(username)
                .password(password)
                //roles(Set.of(AccountRole.ADMIN, AccountRole.USER)) ---- over jdk9
                .roles(Stream.of(AccountRole.ADMIN, AccountRole.USER).collect(Collectors.toSet()))
                .build();

        this.accountRepository.save(account);


        // When
        UserDetailsService userDetailsService = (UserDetailsService)accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByUsernaeFail(){
        String username = "randomname@google.com";
        accountService.loadUserByUsername(username);
    }

    @Test
    public void findByUsernaeFail2(){
        String username = "randomname@google.com";

        try{
            accountService.loadUserByUsername(username);

            // if no error exception, this test is fail.
            fail("supposed to be failed");
        }catch (UsernameNotFoundException e){
            assertThat(e.getMessage()).containsSequence(username);
        }
    }

    @Test
    public void findByUsernaeFail3(){
        // Expected
        // 예측되는 결과를 먼저 윗 쪽에 적어놔야한다.
        String username = "randomname@google.com";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));

        // When
        accountService.loadUserByUsername(username);
    }

}