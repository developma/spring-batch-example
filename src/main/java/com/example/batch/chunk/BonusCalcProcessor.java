package com.example.batch.chunk;

import com.example.batch.domain.Bonus;
import com.example.batch.domain.Emp;
import org.springframework.batch.item.ItemProcessor;

/**
 * This class is for calculating a bonus of employee.
 * <li>
 *     <ol>Grade 1: Pays price of FIXED_BONUS as a bonus.</ol>
 *     <ol>From Grade2 to Grade 5: Pays calculating value which are combine BASIC_SALARY and BONUS_MAGNIFICATION.</ol>
 * </li>
 */
public class BonusCalcProcessor implements ItemProcessor<Emp, Bonus> {

    @Override
    public Bonus process(Emp emp) throws Exception {
        if (emp == null) {
            throw new IllegalArgumentException("emp must not be null.");
        }
        final Bonus bonus = new Bonus();
        bonus.setEmpId(emp.getEmpId());
        bonus.setPayments(calcPaymeents(emp));
        return bonus;
    }

    private Integer calcPaymeents(Emp emp) {
        if (emp.getGrade().getFixedBonus() != null) {
            return emp.getGrade().getFixedBonus();
        } else {
            return emp.getGrade().getBonusMagnification()
                    * emp.getBasicSalary() / 100;
        }
    }
}
