--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.3
-- Dumped by pg_dump version 9.3.3
-- Started on 2014-03-12 14:23:05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE "SocialNetworkDB";
--
-- TOC entry 1994 (class 1262 OID 16393)
-- Name: SocialNetworkDB; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "SocialNetworkDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Turkish_Turkey.1254' LC_CTYPE = 'Turkish_Turkey.1254';


ALTER DATABASE "SocialNetworkDB" OWNER TO postgres;

\connect "SocialNetworkDB"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 1995 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 176 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1997 (class 0 OID 0)
-- Dependencies: 176
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 190 (class 1255 OID 16488)
-- Name: follow(bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION follow(par_following bigint, par_follower bigint) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN
INSERT INTO followrelation(member_id,follower_id)
VALUES (par_following, par_follower);
END;$$;


ALTER FUNCTION public.follow(par_following bigint, par_follower bigint) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 16410)
-- Name: status; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE status (
    status_id bigint NOT NULL,
    sender_id bigint NOT NULL,
    status_date date NOT NULL,
    status_text character varying NOT NULL
);


ALTER TABLE public.status OWNER TO postgres;

--
-- TOC entry 193 (class 1255 OID 16492)
-- Name: getallstatusposts(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION getallstatusposts(par_sender_id bigint) RETURNS SETOF status
    LANGUAGE plpgsql
    AS $$BEGIN
SELECT * FROM status WHERE par_sender_id = sender_id;
END;$$;


ALTER FUNCTION public.getallstatusposts(par_sender_id bigint) OWNER TO postgres;

--
-- TOC entry 191 (class 1255 OID 16487)
-- Name: getfollowers(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION getfollowers(par_member_id bigint) RETURNS SETOF bigint
    LANGUAGE plpgsql
    AS $$BEGIN
SELECT follower_id FROM followrelation WHERE member_id = par_member_id;
END;$$;


ALTER FUNCTION public.getfollowers(par_member_id bigint) OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 16399)
-- Name: member; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE member (
    member_id bigint NOT NULL,
    status_id_count integer DEFAULT 0,
    member_name character varying NOT NULL,
    member_email character varying,
    member_password character varying,
    member_visibility boolean DEFAULT true
);


ALTER TABLE public.member OWNER TO postgres;

--
-- TOC entry 197 (class 1255 OID 16490)
-- Name: getuserdatafromid(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION getuserdatafromid(par_member_id bigint) RETURNS SETOF member
    LANGUAGE sql
    AS $$
SELECT * FROM member WHERE par_member_id = member_id;
$$;


ALTER FUNCTION public.getuserdatafromid(par_member_id bigint) OWNER TO postgres;

--
-- TOC entry 189 (class 1255 OID 16469)
-- Name: getvisibleusers(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION getvisibleusers() RETURNS SETOF member
    LANGUAGE plpgsql
    AS $$BEGIN
SELECT * FROM member WHERE member_visibility = true;
END;$$;


ALTER FUNCTION public.getvisibleusers() OWNER TO postgres;

--
-- TOC entry 196 (class 1255 OID 16495)
-- Name: poststatus(bigint, character varying, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION poststatus(par_member_id bigint, par_status_text character varying, par_date date) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN
INSERT INTO status(sender_id,status_text,status_date)
VALUES (par_member_id, par_status_text, par_date);
END;$$;


ALTER FUNCTION public.poststatus(par_member_id bigint, par_status_text character varying, par_date date) OWNER TO postgres;

--
-- TOC entry 192 (class 1255 OID 16491)
-- Name: unfollow(bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION unfollow(par_unfollowed_person_id bigint, par_unfollower_id bigint) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN
DELETE FROM followrelation 
WHERE par_unfollowed_person_id = member_id
AND par_unfollower_id = follower_id;
END;$$;


ALTER FUNCTION public.unfollow(par_unfollowed_person_id bigint, par_unfollower_id bigint) OWNER TO postgres;

--
-- TOC entry 195 (class 1255 OID 16494)
-- Name: userlogin(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION userlogin(par_member_password character varying, par_member_email character varying) RETURNS bigint
    LANGUAGE sql
    AS $$SELECT member_id FROM member
where par_member_email = member_email
and par_member_password = member_password;
$$;


ALTER FUNCTION public.userlogin(par_member_password character varying, par_member_email character varying) OWNER TO postgres;

--
-- TOC entry 194 (class 1255 OID 16493)
-- Name: userregister(character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION userregister(par_member_name character varying, par_member_password character varying, par_member_email character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN
	INSERT INTO member(member_name, member_email, member_password)
	VALUES(par_member_name, par_member_email, par_member_password);
END;$$;


ALTER FUNCTION public.userregister(par_member_name character varying, par_member_password character varying, par_member_email character varying) OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 16471)
-- Name: followrelation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE followrelation (
    id bigint NOT NULL,
    member_id bigint NOT NULL,
    follower_id bigint NOT NULL
);


ALTER TABLE public.followrelation OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 16419)
-- Name: image; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE image (
    image bytea,
    image_owner bigint,
    image_id bigint NOT NULL
);


ALTER TABLE public.image OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 16397)
-- Name: member_member_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE member_member_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.member_member_id_seq OWNER TO postgres;

--
-- TOC entry 1998 (class 0 OID 0)
-- Dependencies: 170
-- Name: member_member_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE member_member_id_seq OWNED BY member.member_id;


--
-- TOC entry 172 (class 1259 OID 16408)
-- Name: status_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE status_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.status_status_id_seq OWNER TO postgres;

--
-- TOC entry 1999 (class 0 OID 0)
-- Dependencies: 172
-- Name: status_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE status_status_id_seq OWNED BY status.status_id;


--
-- TOC entry 1849 (class 2604 OID 16402)
-- Name: member_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY member ALTER COLUMN member_id SET DEFAULT nextval('member_member_id_seq'::regclass);


--
-- TOC entry 1852 (class 2604 OID 16413)
-- Name: status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY status ALTER COLUMN status_id SET DEFAULT nextval('status_status_id_seq'::regclass);


--
-- TOC entry 1989 (class 0 OID 16471)
-- Dependencies: 175
-- Data for Name: followrelation; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1988 (class 0 OID 16419)
-- Dependencies: 174
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1985 (class 0 OID 16399)
-- Dependencies: 171
-- Data for Name: member; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO member (member_id, status_id_count, member_name, member_email, member_password, member_visibility) VALUES (3, 0, 'asd', 'asd', 'asd', true);
INSERT INTO member (member_id, status_id_count, member_name, member_email, member_password, member_visibility) VALUES (4, 0, '123', '123', '123', true);


--
-- TOC entry 2000 (class 0 OID 0)
-- Dependencies: 170
-- Name: member_member_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('member_member_id_seq', 4, true);


--
-- TOC entry 1987 (class 0 OID 16410)
-- Dependencies: 173
-- Data for Name: status; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2001 (class 0 OID 0)
-- Dependencies: 172
-- Name: status_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('status_status_id_seq', 1, false);


--
-- TOC entry 1870 (class 2606 OID 16499)
-- Name: followrelation_id_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY followrelation
    ADD CONSTRAINT followrelation_id_ukey UNIQUE (id);


--
-- TOC entry 1872 (class 2606 OID 16475)
-- Name: followrelation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY followrelation
    ADD CONSTRAINT followrelation_pkey PRIMARY KEY (id);


--
-- TOC entry 1866 (class 2606 OID 16501)
-- Name: image_id_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_id_ukey UNIQUE (image_id);


--
-- TOC entry 1868 (class 2606 OID 16426)
-- Name: image_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_pkey PRIMARY KEY (image_id);


--
-- TOC entry 1854 (class 2606 OID 16497)
-- Name: member_email_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY member
    ADD CONSTRAINT member_email_ukey UNIQUE (member_email);


--
-- TOC entry 1856 (class 2606 OID 16503)
-- Name: member_id_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY member
    ADD CONSTRAINT member_id_ukey UNIQUE (member_id);


--
-- TOC entry 1858 (class 2606 OID 16407)
-- Name: member_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY member
    ADD CONSTRAINT member_pkey PRIMARY KEY (member_id);


--
-- TOC entry 1861 (class 2606 OID 16505)
-- Name: status_id_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY status
    ADD CONSTRAINT status_id_ukey UNIQUE (status_id);


--
-- TOC entry 1863 (class 2606 OID 16415)
-- Name: status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY status
    ADD CONSTRAINT status_pkey PRIMARY KEY (status_id);


--
-- TOC entry 1864 (class 1259 OID 16432)
-- Name: fki_image_owner_fkey; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_image_owner_fkey ON image USING btree (image_owner);


--
-- TOC entry 1859 (class 1259 OID 16438)
-- Name: fki_status_sender_id_fkey; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_status_sender_id_fkey ON status USING btree (sender_id);


--
-- TOC entry 1876 (class 2606 OID 16481)
-- Name: followrelation_follower_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY followrelation
    ADD CONSTRAINT followrelation_follower_id_fkey FOREIGN KEY (follower_id) REFERENCES member(member_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1875 (class 2606 OID 16476)
-- Name: followrelation_member_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY followrelation
    ADD CONSTRAINT followrelation_member_id_fkey FOREIGN KEY (member_id) REFERENCES member(member_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1874 (class 2606 OID 16427)
-- Name: image_owner_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_owner_fkey FOREIGN KEY (image_owner) REFERENCES member(member_id);


--
-- TOC entry 1873 (class 2606 OID 16433)
-- Name: status_sender_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY status
    ADD CONSTRAINT status_sender_id_fkey FOREIGN KEY (sender_id) REFERENCES member(member_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1996 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-03-12 14:23:05

--
-- PostgreSQL database dump complete
--

