--Group hosts by hardware info
SELECT
    cpu_number,id,total_mem
FROM host_info
ORDER BY total_mem DESC;

CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
    $$
BEGIN
RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
LANGUAGE PLPGSQL;
--Average memory usage
SELECT
    u.host_id,
    i.hostname,
    round5(u.timestamp) as time_stamp,
    Round(AVG(((i.total_mem/1000) - u.memory_free)/100) ,2)as avg_used_mem_percentage
FROM host_info as i
    INNER JOIN host_usage as u
    ON i.id = u.host_id
GROUP BY
    u.host_id,
    i.hostname,
    time_stamp
ORDER BY
    u.host_id ASC,
    time_stamp ASC;
--Detect host failure
SELECT
    host_id,
    round5("timestamp") as time_stamp,
    count(*) as Value_inserted
FROM
    host_usage
GROUP BY
    host_id,
    time_stamp
HAVING
    count(*) < 3;