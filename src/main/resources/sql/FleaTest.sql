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