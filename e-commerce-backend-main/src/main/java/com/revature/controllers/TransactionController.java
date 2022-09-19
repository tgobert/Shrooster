package com.revature.controllers;

import com.revature.dtos.TransactionDto;
import com.revature.models.Transaction;
import com.revature.services.TransactionService;
import com.revature.util.JwtTokenManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000", "http://e-commerce-shrooster.s3-website-us-east-1.amazonaws.com" }, allowCredentials = "true")
public class TransactionController {

    private TransactionService ts;
    JwtTokenManager tm;

    public TransactionController(TransactionService ts, JwtTokenManager tm) {
        this.ts = ts;
        this.tm = tm;
    }

    @GetMapping
    public Optional<List<Transaction>> findByUserId(@RequestHeader("rolodex-token") String token) {
        int userId = tm.parseUserIdFromToken(token);
        return ts.findByUserId(userId);
    }

    @PostMapping
    public Transaction add(@Valid @RequestBody TransactionDto newTran,
            @RequestHeader("rolodex-token") String token) {
        int userId = tm.parseUserIdFromToken(token);
        newTran.setUserId(userId);

        return ts.add(newTran);

    }

}
