SELECT	ROW_NUMBER() OVER (ORDER BY BOARD_NO ASC) "RNUM",
          BOARD_NO, BOARD_TITLE, READ_COUNT, MEMBER_NICKNAME,
          (SELECT PRICE FROM FLEA_BOARD F WHERE F.BOARD_NO = B.BOARD_NO) AS PRICE,
          (SELECT GOODS FROM FLEA_BOARD F WHERE F.BOARD_NO = B.BOARD_NO) AS GOODS,

--         <![CDATA[
--     CASE
--     WHEN CURRENT_DATE - BOARD_WRITE_DATE < 1 / 24 / 60
--                           THEN  FLOOR((CURRENT_DATE - BOARD_WRITE_DATE) * 24 * 60 * 60) || '초 전'
--
--                       WHEN CURRENT_DATE - BOARD_WRITE_DATE < 1 / 24
--                           THEN  FLOOR((CURRENT_DATE - BOARD_WRITE_DATE) * 24 * 60) || '분 전'
--
--                       WHEN CURRENT_DATE - BOARD_WRITE_DATE < 1
--                           THEN  FLOOR((CURRENT_DATE - BOARD_WRITE_DATE) * 24) || '시간 전'
--
--                       ELSE TO_CHAR(BOARD_WRITE_DATE, 'YYYY-MM-DD')
--                       END AS "BOARD_WRITE_DATE"
-- 						]]>
-- 						,
                  (SELECT	IMG_PATH || IMG_RENAME
                   FROM		BOARD_IMG I
                   WHERE 	IMG_ORDER = 0
                     AND 		I.BOARD_NO = B.BOARD_NO
                  ) AS THUMBNAIL
        FROM	"BOARD" B
                    JOIN	"MEMBER" M ON (B.MEMBER_NO = M.MEMBER_NO)
        WHERE	BOARD_DEL_FL = 'N'
          AND		BOARD_CODE = 3
        ORDER BY RNUM DESC;

SELECT  COUNT(*)
FROM    "BOARD"
WHERE   BOARD_CODE = 3
  AND     BOARD_DEL_FL = 'N';




SELECT	COUNT(*)
FROM	"BOARD"
JOIN "MEMBER" USING(MEMBER_NO)
JOIN FLEA_BOARD USING(BOARD_NO)

WHERE	BOARD_CODE = 3
AND		BOARD_DEL_FL = 'N'

-- AND BOARD_TITLE LIKE '%' || '10' || '%';



-- AND BOARD_CONTENT LIKE '%' || '10' || '%';


-- AND (
-- BOARD_TITLE LIKE '%' || '10' || '%'
-- OR
-- BOARD_CONTENT LIKE '%' || '10' || '%'
-- );

-- AND MEMBER_NICKNAME LIKE '%' || '이' || '%';


AND GOODS LIKE '%' || '10' || '%';


SELECT	CEIL(RNUM/10) CP, BOARD_NO
FROM
    (SELECT	ROW_NUMBER() OVER(ORDER BY BOARD_NO DESC) RNUM, BOARD_NO
     FROM	"BOARD"


            JOIN FLEA_BOARD USING(BOARD_NO)


        WHERE	BOARD_CODE = 3
        AND		BOARD_DEL_FL = 'N'


                AND GOODS LIKE '%' || '1' || '%'

        );
--         WHERE	BOARD_NO = 3;






INSERT INTO "BOARD"
(BOARD_NO, BOARD_TITLE, BOARD_CONTENT,
 BOARD_WRITE_DATE, BOARD_UPDATE_DATE, READ_COUNT, BOARD_DEL_FL,
 MEMBER_NO, BOARD_CODE)
VALUES (    SEQ_BOARD_NO.NEXTVAL, 'TestTitle', 'TestContent',
                    DEFAULT, NULL, DEFAULT, DEFAULT,
    0, 3);

INSERT INTO "FLEA_BOARD"
(PRICE, GOODS)
VALUES  (12345, 'TestGoods')
WHERE   BOARD_NO = SEQ_BOARD_NO.CURRVAL;

SELECT * FROM BOARD_IMG;






SELECT	BOARD_NO,
          BOARD_TITLE,
          BOARD_CONTENT,
          BOARD_CODE,
          READ_COUNT,
          B.MEMBER_NO,
          MEMBER_NICKNAME,
          PROFILE_IMG,
          TO_CHAR(BOARD_WRITE_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS')
                                                                         AS BOARD_WRITE_DATE,
          TO_CHAR(BOARD_UPDATE_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS')
                                                                         AS BOARD_UPDATE_DATE,

          (	SELECT	IMG_PATH || IMG_RENAME
               FROM	BOARD_IMG I
               WHERE	I.BOARD_NO = #{boardNo}
                         AND		IMG_ORDER = 0
          ) AS THUMBNAIL,
          (SELECT PRICE FROM FLEA_BOARD F WHERE F.BOARD_NO = B.BOARD_NO) AS PRICE,
          (SELECT GOODS FROM FLEA_BOARD F WHERE F.BOARD_NO = B.BOARD_NO) AS GOODS

FROM	"BOARD" B
            JOIN	"MEMBER" M ON (B.MEMBER_NO = M.MEMBER_NO)
WHERE	BOARD_NO = 885
          AND		BOARD_CODE = 3;

select * from BOARD where BOARD_CODE = 3 order by BOARD_NO desc ;





INSERT INTO REPORT_BOARD
VALUES (4, 1011, '테스트에요', DEFAULT, DEFAULT, 1);

INSERT INTO MEMBER
VALUES (SEQ_MEMBER_NO.nextval, '@.com', '$2a$10$uXKU/evWI2gdJTjfNVeaM.txw6Wjz4LzRYfc56cC8u/03ZQgiTVzm', '테스트','청계천',null, default, default, default);

commit ;
SELECT  * FROM MEMBER;

SELECT * FROM REPORT_BOARD;
COMMIT;
SELECT * FROM REPORT_COMMENT;

SELECT * FROM BOARD_COMMENT JOIN BOARD USING (BOARD_NO) WHERE BOARD_CODE = 3;



SELECT	CEIL(RNUM) CP
FROM
    (SELECT	ROW_NUMBER() OVER(ORDER BY BOARD_NO DESC) RNUM, BOARD_NO
     FROM	"BOARD"

        WHERE	BOARD_CODE = 3
        AND		BOARD_DEL_FL = 'N'

        )
        WHERE	BOARD_NO = 3;