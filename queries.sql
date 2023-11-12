--Part 1
CREATE TABLE job (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    employer VARCHAR(255),
    skills VARCHAR(255)
);
-- SELECT * FROM job

--Part 2
SELECT name
FROM employers
WHERE location = 'St. Louis City';

--Part 3
DROP TABLE job;

--Part 4
SELECT * FROM skill
LEFT JOIN job_skills ON skill.id = job_skills.skills_id OR job_skills.skills_id = skill.id
WHERE job_skills.jobs_id IS NOT NULL
ORDER BY name ASC;


select * from job
