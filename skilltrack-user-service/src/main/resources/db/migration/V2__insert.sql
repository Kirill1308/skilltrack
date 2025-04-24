INSERT INTO departments (id, name, description, manager_id)
VALUES
    (gen_random_uuid(),'Engineering', 'Software development and engineering teams', NULL),
    (gen_random_uuid(),'Product', 'Product management and design', NULL),
    (gen_random_uuid(),'Marketing', 'Brand development and customer acquisition', NULL),
    (gen_random_uuid(),'Sales', 'Customer relationships and revenue generation', NULL),
    (gen_random_uuid(),'Finance', 'Financial planning and accounting', NULL),
    (gen_random_uuid(),'Human Resources', 'Recruiting and employee development', NULL),
    (gen_random_uuid(),'Customer Support', 'UserProfile assistance and issue resolution', NULL),
    (gen_random_uuid(),'Operations', 'Business processes and infrastructure', NULL),
    (gen_random_uuid(),'Research', 'Innovation and future product development', NULL),
    (gen_random_uuid(),'Legal', 'Legal compliance and contract management', NULL);
