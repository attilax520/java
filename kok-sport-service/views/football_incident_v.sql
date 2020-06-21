DROP VIEW IF EXISTS `football_incident_v`;
CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `football_incident_v` AS

select *  from `football_incident_t`;