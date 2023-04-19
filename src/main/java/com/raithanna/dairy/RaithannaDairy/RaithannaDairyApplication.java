package com.raithanna.dairy.RaithannaDairy;

import com.raithanna.dairy.RaithannaDairy.models.dailySales;
import com.raithanna.dairy.RaithannaDairy.models.productMaster;
import com.raithanna.dairy.RaithannaDairy.repositories.DailySalesRepository;
import com.raithanna.dairy.RaithannaDairy.repositories.ProductMasterRepository;
import com.raithanna.dairy.RaithannaDairy.repositories.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@RestController
@Controller
public class RaithannaDairyApplication {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductMasterRepository productMasterRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(RaithannaDairyApplication.class, args);

    }
}