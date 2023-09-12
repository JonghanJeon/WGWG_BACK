package kb.wgwg.controller;

import kb.wgwg.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/banking")
public class BankingController {
    private final BankingService bankingService;

}
