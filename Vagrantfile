# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure("2") do |config|

  config.vm.box = "ubuntu/zesty64"
  config.vm.box_url = "https://atlas.hashicorp.com/ubuntu/boxes/zesty64/versions/20161117.0.0/providers/virtualbox.box"

  # config.vm.network "forwarded_port", guest: 80, host: 8080

  config.vm.network "private_network", ip: "192.168.33.10"
  # config.vm.network "public_network"

  # config.vm.synced_folder "../data", "/vagrant_data"
  # config.vm.provider "virtualbox" do |vb|
  #   # Display the VirtualBox GUI when booting the machine
  #   vb.gui = true
  #   vb.memory = "1024"
  # end
  #
  config.vm.provision "shell", path: "provision.sh"
  # config.vm.provision "shell", inline: <<-SHELL
  #    apt-get update

  # SHELL
end
