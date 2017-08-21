package com.example.batch.chunk;

import com.example.batch.domain.Bonus;
import com.example.batch.domain.Emp;
import com.example.batch.domain.Grade;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class BonusCalcProcessorTest {

    private BonusCalcProcessor sut = new BonusCalcProcessor();

    @Test
    public void testFixedBonus() throws Exception {
        final Emp emp = new Emp();
        emp.setEmpId(1);
        emp.setEname("test");
        emp.setBasicSalary(100000);
        final Grade grade = new Grade();
        grade.setGradeCode(1);
        grade.setFixedBonus(200000);
        emp.setGrade(grade);

        final Bonus bonus = sut.process(emp);

        assertThat(bonus.getPayments(), is(200000));
    }

    @Test
    public void testMagnificationBonus() throws Exception {
        final Emp emp = new Emp();
        emp.setEmpId(2);
        emp.setBasicSalary(100000);
        final Grade grade = new Grade();
        grade.setGradeCode(2);
        grade.setBonusMagnification(150);
        emp.setGrade(grade);

        final Bonus bonus = sut.process(emp);

        assertThat(bonus.getPayments(), is(150000));
    }

    @Test
    public void testNull() throws Exception {
        try {
            sut.process(null);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("emp must not be null."));
        }
    }
}
