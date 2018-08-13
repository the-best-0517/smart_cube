		insert into t_lg_people
      		(
      		  name,
      		  pwd,
      		  modify_user,
      		  create_time,
      		  phone
      		)values
      		(
      			#{name},
      			#{pwd},
      			623,
      			NOW(),
      			#{phone}
      		)