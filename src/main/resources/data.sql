INSERT INTO GRADE (GRADE_CODE, BONUS_MAGNIFICATION, FIXED_BONUS)
       VALUES
           (1, NULL, 150000),
           (2, 150, NULL),
           (3, 200, NULL),
           (4, 250, NULL),
           (5, 300, NULL)
       ON CONFLICT DO NOTHING;
       
INSERT INTO EMP (EMP_ID, EMP_NAME, BASIC_SALARY, GRADE_CODE)
       VALUES
           (1, 'SCOTT', 200000, 1),
           (2, 'ADAM', 250000, 2),
           (3, 'MIKEs', 300000, 3),
           (4, 'PHIL', 350000, 4),
           (5, 'JACK', 400000, 5)
       ON CONFLICT DO NOTHING;
