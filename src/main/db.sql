-- Table: public.tasks

-- DROP TABLE IF EXISTS public.tasks;

CREATE TABLE IF NOT EXISTS public.tasks
(
    id integer NOT NULL,
    description character varying(512) COLLATE pg_catalog."default",
    initial_date date,
    due_date date,
    priority character varying(10) COLLATE pg_catalog."default",
    status character varying(15) COLLATE pg_catalog."default",
    project character varying(32) COLLATE pg_catalog."default",
    CONSTRAINT tasks_pkey PRIMARY KEY (id)
)


            -- Table: public.files

            -- DROP TABLE IF EXISTS public.files;

            CREATE TABLE IF NOT EXISTS public.files
            (
                id integer NOT NULL,
                task_id integer,
                name character varying(50) COLLATE pg_catalog."default",
                path character varying(60) COLLATE pg_catalog."default",
                CONSTRAINT files_pkey PRIMARY KEY (id),
                CONSTRAINT files_task_id_fkey FOREIGN KEY (task_id)
                    REFERENCES public.tasks (id) MATCH SIMPLE
                    ON UPDATE NO ACTION
                    ON DELETE NO ACTION
            )

             -- Table: public.contacts

              -- DROP TABLE IF EXISTS public.contacts;
             create table contacts(
                                    id integer,
                					task_id integer,
                					email_address character varying(50),
                					primary key (id),
                					foreign key(task_id) references tasks (id)
                )



            -- Table: public.users

            -- DROP TABLE IF EXISTS public.users;

 CREATE TABLE IF NOT EXISTS public.users
            (
                username character varying(32) COLLATE pg_catalog."default" NOT NULL,
                password character varying(100) COLLATE pg_catalog."default",
                CONSTRAINT users_pkey PRIMARY KEY (username)
            )

        -- Table: public.user_task_relation

        -- DROP TABLE IF EXISTS public.user_task_relation;

 CREATE TABLE IF NOT EXISTS public.user_task_relation
        (
            id integer NOT NULL,
            username character varying(32) COLLATE pg_catalog."default",
            task_id integer,
            CONSTRAINT user_task_relation_pkey PRIMARY KEY (id),
            CONSTRAINT user_task_relation_task_id_fkey FOREIGN KEY (task_id)
                REFERENCES public.tasks (id) MATCH SIMPLE
                ON UPDATE NO ACTION
                ON DELETE NO ACTION,
            CONSTRAINT user_task_relation_username_fkey FOREIGN KEY (username)
                REFERENCES public.users (username) MATCH SIMPLE
                ON UPDATE NO ACTION
                ON DELETE NO ACTION
        )



