package com.raithanna.dairy.RaithannaDairy.controller;
import com.raithanna.dairy.RaithannaDairy.models.purchaseOrder;
import com.raithanna.dairy.RaithannaDairy.models.supplier;
import com.raithanna.dairy.RaithannaDairy.repositories.CustomerRepository;
import com.raithanna.dairy.RaithannaDairy.repositories.PurchaseOrderRepository;
import com.raithanna.dairy.RaithannaDairy.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class purchaseOrderController {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @GetMapping("/purchase")
    public String purchaseOrderForm(Model model,HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
        List<supplier> Suppliers = supplierRepository.findByOrderByIdDesc();
       List<purchaseOrder>Amts= purchaseOrderRepository.findByOrderByAmtDesc();
        System.out.println(Suppliers.size());
        purchaseOrder po = new purchaseOrder();
        model.addAttribute("purchase", po);
        model.addAttribute("supplier", Suppliers);
        model.addAttribute("amt",Amts);
        return "purchase";
    }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }


    @GetMapping("/purchaseExcelData")
    public String purchaseExcelOrderForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            List<supplier> Suppliers = supplierRepository.findByOrderByIdDesc();
            purchaseOrder po = new purchaseOrder();
            model.addAttribute("purchase", po);
            model.addAttribute("supplier", Suppliers);
            return "purchaseExcel";
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }


    @PostMapping("/purchase")
    public String savePurchases(@RequestParam Map<String, String> body, Model model) {
        System.out.println(body);
        purchaseOrder po = new purchaseOrder();
        System.out.println("SupplierCode:" + body.get("code"));
        po.setSupplier(body.get("supplierName"));
        po.setInvDate(LocalDate.parse(body.get("invDate")));
        po.setSnfP(Double.parseDouble(body.get("snfP")));
        po.setFatP(Double.parseDouble(body.get("fatP")));
        po.setTsRate(Double.parseDouble(body.get("tsRate")));
        po.setQuantity(Double.parseDouble(body.get("quantity")));
        po.setMilkType(body.get("milkType"));
        po.setCode(body.get("supplierName"));
        po.setBankName(body.get("bankName"));
        po.setPaymentStatus(body.get("paymentStatus"));
        double ltrRate;
        ltrRate = ((Double.parseDouble(body.get("fatP")) + Double.parseDouble(body.get("snfP"))) * Double.parseDouble(body.get("tsRate"))) / 100;
        po.setLtrRate(ltrRate);
        purchaseOrder purchaseOrder = purchaseOrderRepository.findTopByOrderBySlNoDesc();
        Integer slNo;
        if (purchaseOrder == null) {
            slNo = 1;
        } else {
            slNo = purchaseOrder.getSlNo() + 1;
        }
        po.setSlNo(slNo);
        Double amt;
        amt = (Double.parseDouble(body.get("quantity")) * ltrRate);
        po.setAmt(amt);


        // date order
        //countDistinctRackById(Long id);
        Integer mainOrderCount = purchaseOrderRepository.countDistinctSupplierByInvDate(po.getInvDate());

        System.out.println("************" + mainOrderCount);

        //List<purchaseOrder> list = purchaseOrderRepository.findBySupplierAndInvDate(po.getSupplier(), po.getInvDate());

        // main order logic
        List<purchaseOrder> list = purchaseOrderRepository.findBySupplierAndInvDate(po.getSupplier(), po.getInvDate());

        //sub order logic
        Integer subOrder = purchaseOrderRepository.countBySupplierAndInvDate(po.getSupplier(), po.getInvDate());
        Integer orderNo1 = subOrder + 1;

        System.out.println("dayWise sub order--- " + orderNo1);
        if(list.size()>0) {
            String invNumber = list.get(0).getInvNo();  // DB Invoice Number --- FD-2023-04-23-VDPL_007-001/1
            String[] arrOfStr = invNumber.split("/", 2);
            System.out.println("arrOfStr --- " + arrOfStr[0]);
            System.out.println("arrOfStr --- " + arrOfStr[1]);


            System.out.println("dayWise main order --- " + list.size());
            System.out.println("DB Invoice Number --- " +invNumber);

            String invNo = arrOfStr[0]+"/" + orderNo1.toString();
            po.setInvNo(invNo);
        }else{
            System.out.println("dayWise main order --- " + list.size());
            //001
            String format = String.format("%03d", list.size() + 1);
            String invNo = "FD-" + po.getInvDate() + "-" + po.getSupplier() + "-" + format + "/" + orderNo1.toString();
            po.setInvNo(invNo);
        }





        purchaseOrderRepository.save(po);
        Map<String, String> respBody = new HashMap<>();
        return "redirect:/purchase";
    }

    @PostMapping("/purchaseExcelData")
    public String purchaseExcelData(@RequestParam Map<String, String> body, Model model) {
        System.out.println(body);
        body.get("supplierName");
        LocalDate.parse(body.get("invDate"));
        Double.parseDouble(body.get("snfP"));
        Double.parseDouble(body.get("fatP"));
        Double.parseDouble(body.get("tsRate"));
        Double.parseDouble(body.get("quantity"));
        body.get("milkType");
        body.get("supplierName");
        body.get("bankName");
        body.get("paymentStatus");

        return "redirect:/purchaseExcelData";
    }

//    @GetMapping("view/{id}")
//        public String viewPurchaseOrderForm(Model model, @PathVariable(name="id") Integer id ,HttpSession session) {
//            if (session.getAttribute("loggedIn").equals("yes")) {
//                purchaseOrder po = purchaseOrderRepository.findById();
//
//                System.out.println("purchaseOrder Amount:"+po.getAmt());
//                model.addAttribute("amt",po.getAmt());
//                model.addAttribute("supplier",po.getSupplier());
//                model.addAttribute("ltrRate",po.getLtrRate());
//                model.addAttribute("tsRate",po.getTsRate());
//                model.addAttribute("snfP",po.getSnfP());
//                model.addAttribute("fatP",po.getFatP());
//                model.addAttribute("quantity",po.getQuantity());
//                model.addAttribute("invDate",po.getInvDate());
//                model.addAttribute("slNo",po.getSlNo());
//            }else{
//            List messages = new ArrayList<>();
//            messages.add("Login First");
//            model.addAttribute("messages", messages);
//            return "redirect:/loginPage";
//        }
//            return "viewPurchaseOrder";
//
//    }
}
