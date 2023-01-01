CREATE OR REPLACE procedure HR.proc_chatlogin(u_id IN varchar2
                                   , u_pw IN varchar2
                                   , nick_name OUT varchar2)
is
    r_status number;
begin 
    SELECT NVL((SELECT 1 FROM chat_member
                WHERE mem_id=u_id), -1) INTO r_status
      FROM dual;
    IF r_status=1 THEN
        SELECT NVL((SELECT mem_nick FROM chat_member
                     WHERE mem_id=u_id
                       AND mem_pw=u_pw
                      ), '비번이 틀립니다.') INTO nick_name
          FROM dual;   
    ELSIF r_status=-1 THEN
        nick_name:='아이디가 존재하지 않습니다.';           
    END IF;
end;
/