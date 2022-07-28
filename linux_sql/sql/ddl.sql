--(optional) switch to `host_agent`

--create `host_info` table if not exist
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
(
    id               SERIAL NOT NULL,
    hostname         VARCHAR NOT NULL,
    cpu_number       integer NOT NULL,
    cpu_architecture varchar NOT NULL,
    cpu_model        varchar NOT NULL,
    cpu_mhz          numeric(10,3) NOT NULL,
    L2_cache         integer NOT NULL,
    total_mem        integer NOT NULL,
    "timestamp"       timestamp NOT NULL,
    CONSTRAINT hostname_unique UNIQUE (hostname),
    CONSTRAINT host_info_id_pk PRIMARY KEY (id)
);

--create `host_usage` table if not exist
CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    "timestamp"    TIMESTAMP NOT NULL,
    host_id        SERIAL NOT NULL,
    memory_free    integer NOT NULL,
    cpu_idle       integer NOT NULL,
    cpu_kernel     smallint NOT NULL,
    disk_io        smallint NOT NULL,
    disk_available integer NOT NULL,
    CONSTRAINT host_usage_id_fk FOREIGN KEY (host_id)
    REFERENCES public.host_info (id)
);
