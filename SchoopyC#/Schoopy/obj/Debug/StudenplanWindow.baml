   M S B A M L   `   `   `  ����B  >Schoopy, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null!clr-namespace:SchoopySchoopy  5   	       Schoopy.StudenplanWindow   5   	   W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   'localclr-namespace:Schoopy     5   	     � Title$  
Studenplan��5   	   $	��300��6   $��512.658��6)   ���#2e3137.��5	      � .+�5
   
   o� -   5         o�Name$
 menu��5
      $
��Left=�6   $��25��68   $	��Top=�6D   $	��505��6\   ���#2e3137
�������.@�5      m� -   5        $��Stundenplan��5      5      m� 5      -   5        $��	Dokumente��5      $��25��6C   5      m� 5      -   5        $��Schüler��5      5      5      &   System.Windows.Controls.DataGrid  5   
   -   5          Name$ dataGrid��5      $
��Left=�6%   $��0,50,0,0q�6@   $	��Top=�6R   $	��495��6j   $	��209��6v      SelectionUnit$
 Cell=�6�   ���#2e3137���#2e3137   
IsReadOnly .    Columns 5      0  *System.Windows.Controls.DataGridTextColumn  5      ,  &System.Windows.Controls.DataGridColumn   Header$ Montag��6%      Width5  /System.Windows.Controls.DataGridLengthConverter$ * 65   -   5        col1��5   ?     5      $ Dienstag��6%   $ * 68   -   5        col2��5   B     5      $ Mittwoch��6%   $ * 68   -   5        col3��5   B     5      $ 
Donnerstag��6%   $ * 6:   -	   5        col4��5   D     5      $ Freitag��6%   $ * 67   -
   5        col5��5   A      	CellStyle 5      �� 5      	 �� 
TargetType*  $System.Windows.Controls.DataGridCell"	  6   5      5       �� 5!   
   -   5        
 ��Name$
 button��5!      $��Details��6!   $
��Left=�63   $��	10,25,0,0q�6N   $	��Top=�6a   $��75��6y   ���#545d6a
�������6�   5%      5&      