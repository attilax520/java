DROP VIEW IF EXISTS `foot_detail_data_recent`;
CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `foot_detail_data_recent` AS



select * from `foot_detail_data_v` order by `foot_detail_data_v`.`tee_time` desc limit 20;