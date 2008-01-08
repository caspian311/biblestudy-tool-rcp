#!/usr/bin/perl

$user = "root";
$pass = "root";
$db = "biblestudy";

@directories = <*>;

foreach $directory (@directories)
{
	if (-d $directory)
	{
		@files = <$directory/*>;

		foreach $file (@files)
		{
			$command = "mysql -u $user -p$pass $db < \"$file\"\n";
			print "$command";
			`$command`;
		}
	}
}
