SELECT * FROM chat_member

SELECT seq_cmember_num.nextval FROM dual

insert into chat_member(mem_num,mem_id, mem_pw, mem_name, mem_nick,mem_hp, mem_birth, reg_date)
values(seq_cmember_num.nextval, 'kiwi','123','키위','나초보','010-555-7777','2000-11-12',to_char(sysdate,'YYYY-MM-DD'))

delete from chat_member
where mem_num in(5,6)

commit;